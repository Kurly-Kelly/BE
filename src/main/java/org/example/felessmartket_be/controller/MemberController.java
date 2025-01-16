package org.example.felessmartket_be.controller;

import com.sun.net.httpserver.HttpsConfigurator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.example.felessmartket_be.domain.Member;
import org.example.felessmartket_be.domain.dto.EmailVerificationResult;
import org.example.felessmartket_be.domain.dto.LoginReqeustDto;
import org.example.felessmartket_be.domain.dto.LoginResponseDto;
import org.example.felessmartket_be.domain.dto.LogoutResponseDto;
import org.example.felessmartket_be.domain.dto.MemberDeleteRequestDto;
import org.example.felessmartket_be.domain.dto.MemberDeleteResponseDto;
import org.example.felessmartket_be.domain.dto.MemberRequestDto;
import org.example.felessmartket_be.domain.dto.MemberResponseDto;
import org.example.felessmartket_be.domain.dto.SingleResponseDto;
import org.example.felessmartket_be.repository.MemberRepository;
import org.example.felessmartket_be.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("")
    public List<Member> readMember() {
        return memberRepository.findAll();
    }

    @PostMapping("/delete")
    public ResponseEntity<MemberDeleteResponseDto> deleteMember(@RequestBody @Valid
        MemberDeleteRequestDto requestDto, HttpServletRequest request) {
        log.info("POST /users/delete 요청: username={}", requestDto.getUsername());

        // 1. Authorization 헤더에서 액세스 토큰 추출
        String bearerToken = request.getHeader("Authorization");
        String token = null;
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7).trim();
        }

        if(token == null) {
            return ResponseEntity.badRequest().body(
                MemberDeleteResponseDto.fail("토큰이 존재하지 않습니다")
            );
        }

        try {
            // 2. Member Service 호출: (DTO, AccessToken) 전달
            MemberDeleteResponseDto responseDto = memberService.deleteMember(requestDto, token);
            if (responseDto.isSuccess()) {
                return ResponseEntity.ok(responseDto);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
            }
        } catch (RuntimeException e) {
            log.error("회원 탈퇴 중 오류 발생: {}", e.getMessage());
            MemberDeleteResponseDto responseDto = MemberDeleteResponseDto.fail(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> createMember(@RequestBody @Valid MemberRequestDto entity) {
        Member createMember = memberService.create(entity);
        MemberResponseDto memberResponseDto = MemberResponseDto.from(createMember);
        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDto);
    }

    @GetMapping("/email")
    public ResponseEntity<Boolean> validateEmail(@RequestParam(value = "e") String email) {
        return ResponseEntity.ok(memberService.checkEmailDuplicate(email));
        // true - 중복되는 이메일 있음
        // false - 중복되는 이메일 없음
    }
    @GetMapping("id")
    public ResponseEntity<Boolean> validateID(@RequestParam(value = "id") String userId) {
        return ResponseEntity.ok(memberService.checkIdDuplicate(userId));
        // 이메일과 동일
    }

    @PostMapping("/email/verification-requests")
    public ResponseEntity<Boolean> sendMessage(@RequestParam("e") @Valid String email) {
        memberService.sendCodeToEmail(email);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/email/verification")
    public ResponseEntity<EmailVerificationResult> verificationEmail(@RequestParam("e") @Valid String email, @RequestParam("code") String authCode) {
        EmailVerificationResult response = memberService.verifiedCode(email, authCode);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginReqeustDto requestDto, HttpServletResponse response) {
        try {
            // 1) Service 호출 -> 로그인 & 토큰 생성
            LoginResponseDto loginResponse = memberService.login(
                requestDto.getUsername(),
                requestDto.getPassword()
            );

            // 2) Controller에서 헤더 세팅
            response.setHeader("Authorization", "Bearer " + loginResponse.getAccessToken());
            response.setHeader("Refresh-Token", "Bearer " + loginResponse.getRefreshToken());

            // 3) DTO 반환(테스트용으로 토큰값 반환받음 -> 추후에 수정 예정)
            return ResponseEntity.ok(loginResponse);
        } catch (RuntimeException e) {
            // 실패 시 메세지 반환 (401 UNAUTHORIZED 등)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
        }
    }

    // 현재 로그인된 사용자의 정보를 반환하는 엔드포인트
    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> getMyInfo() {
        log.info("GET /users/me 요청");

        // 1) 현재 스레드의 SecurityContextHolder 에서 Authentication 객체 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 2) 인증 정보가 없거나 인증되지 않은 상태인지 확인
        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("인증 정보 없음");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 3) Authentication의 principal에서 Member 객체 꺼내옴
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof Member)) {
            // 기대하는 principal 타입이 아닐 경우 처리
            log.warn("예상과 다른 인증 정보 타입: {}", principal.getClass().getName());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Member member = (Member) principal;
        log.info("인증된 사용자 확인: {}", member.getUsername());

        // 4) MemberResponseDto로 변환하여 응답 생성
        MemberResponseDto responseDto = MemberResponseDto.from(member);
        log.info("회원 정보 응답 생성 완료: {}", responseDto);

        // 5) 200 OK와 함께 사용자 정보 반환
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponseDto> logout(HttpServletRequest request) {
        log.info("POST /users/logout 요청");

        // Authorization 헤더에서 토큰 추출
        String bearerToken = request.getHeader("Authorization");
        String token = null;
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7).trim();
        }

        if (token == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(LogoutResponseDto.fail("유효한 토큰이 없습니다."));
        }

        // 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() ||
            !(authentication.getPrincipal() instanceof Member)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(LogoutResponseDto.fail("로그인된 사용자가 아닙니다."));
        }

        Member member = (Member) authentication.getPrincipal();
        LogoutResponseDto responseDto = memberService.logout(member.getUsername(), token);

        if (responseDto.isSuccess()) {
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    // [아이디 찾기] 인증번호 발송
    @PostMapping("/find-id/send-code")
    public ResponseEntity<?> sendFindIdCode(@RequestParam String name, @RequestParam String email) {
        try {
            memberService.sendCodeToEmailForFind(name, email);
            return ResponseEntity.ok("인증번호가 발송되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // [아이디 찾기] 인증번호 확인 후, 아이디 반환
    @PostMapping("/find-id/verify-code")
    public ResponseEntity<?> verifyFindIdCode(@RequestParam String name, @RequestParam String email, @RequestParam String code) {
        try {
            boolean verified = memberService.verifyCodeForFindid(name, email, code);
            if (!verified) {
                return ResponseEntity.badRequest().body("인증번호가 올바르지 않거나 만료되었습니다.");
            }
            // 인증 성공 시 -> username 조회
            String username = memberService.findUsernameByNameAndEmail(name, email);
            return ResponseEntity.ok(username);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}