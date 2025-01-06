package org.example.felessmartket_be.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.example.felessmartket_be.domain.Member;
import org.example.felessmartket_be.domain.dto.EmailVerificationResult;
import org.example.felessmartket_be.domain.dto.LoginReqeustDto;
import org.example.felessmartket_be.domain.dto.LoginResponseDto;
import org.example.felessmartket_be.domain.dto.MemberRequestDto;
import org.example.felessmartket_be.domain.dto.MemberResponseDto;
import org.example.felessmartket_be.domain.dto.SingleResponseDto;
import org.example.felessmartket_be.repository.MemberRepository;
import org.example.felessmartket_be.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> login(@RequestBody @Valid LoginReqeustDto reqeustDto, HttpServletResponse response) {
        try {
            // 1) Service 호출 -> 로그인 & 토큰 생성
            LoginResponseDto loginResponse = memberService.login(
                reqeustDto.getUsername(),
                reqeustDto.getPassword()
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
}