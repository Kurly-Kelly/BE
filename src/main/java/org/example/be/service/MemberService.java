package org.example.be.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.be.domain.Member;
import org.example.be.domain.MemberStatus;
import org.example.be.domain.dto.EmailVerificationResult;
import org.example.be.domain.dto.LoginResponseDto;
import org.example.be.domain.dto.LogoutResponseDto;
import org.example.be.domain.dto.MemberDeleteRequestDto;
import org.example.be.domain.dto.MemberDeleteResponseDto;
import org.example.be.domain.dto.MemberRequestDto;
import org.example.be.jwt.JWTUtil;
import org.example.be.repository.MemberRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class MemberService {

    private static final String AUTH_CODE_PREFIX = "AuthCode ";
    private final MailService mailService;
    private final RedisService redisService;

    private final long authCodeExpirationMillis = 180000;


    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;


    public Member create(MemberRequestDto memberRequestDto) {
        Member newMember = new Member();
        newMember.setUsername(memberRequestDto.getUserName());
        newMember.setName(memberRequestDto.getName());
        newMember.setEmail(memberRequestDto.getEmail());
        newMember.setPhone(memberRequestDto.getPhone());
        newMember.setPassword(passwordEncoder.encode(memberRequestDto.getPassword()));
        System.out.println(newMember);
        memberRepository.save(newMember);
        return newMember;
    }

    public MemberDeleteResponseDto deleteMember(MemberDeleteRequestDto requestDto, String accessToken) {
        // 1) 회원 조회
        Member member = memberRepository.findByUsername(requestDto.getUsername())
            .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        // 2) 이미 탈퇴 상태인지 확인
        if(member.getStatus() == MemberStatus.DELETED) {
            return MemberDeleteResponseDto.fail("이미 탈퇴된 사용자입니다.");
        }

        // 3) 논리적 삭제: status를 DELETED로 변경
        member.setStatus(MemberStatus.DELETED);

        // 4) Redis에서 Refresh Token 삭제
        String refreshTokenKey = "RT: " + requestDto.getUsername();
        redisService.deleteValues(refreshTokenKey);

        // 5) Access Token 블랙리스트 등록
        if (accessToken != null & !accessToken.isEmpty()) {
            redisService.setValues(
                "BL: " + accessToken,
                "true",
                Duration.ofMinutes(30)
            );
            log.info("Access Token Blacklist 추가 완료: {}", requestDto.getUsername());
        }
        log.info("회원 [{}] 탈퇴 처리 완료", requestDto.getUsername());
        return MemberDeleteResponseDto.success("회원 탈퇴가 완료되었습니다.");
    }



    // username, password를 받아서 JWT Access/Refresh 코큰 발급
    public LoginResponseDto login(String username, String password) {
        // 1) DB에서 사용자 정보 조회
        Member member = memberRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        // 탈퇴(비활성) 회원 예외 처리
        if(member.getStatus() == MemberStatus.DELETED) {
            throw new RuntimeException("탈퇴된 사용자입니다. 다시 가입해주세요.");
        }
        // 2) 비밀번호 매칭
        if (member.getPassword().equals(password)) {
            log.warn("평문 비밀번호와 일치함. 암호화된 비밀번호로 변경 필요");
        } else if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 3) 토큰 생성 (Access, Refresh 분리)
        long accessExpiredMs = 30 * 60 * 1000L; // 30분
        long refreshExpiredMs = 7 * 24 * 60 * 60 * 1000L; // 7일

        String accessToken = jwtUtil.createAccessToken(username, "ROLE_USER", accessExpiredMs);
        String refreshToken = jwtUtil.createRefreshToken(username, "ROLE_USER", refreshExpiredMs);

      //  redisService.deleteValues("BL: " + accessToken);

        // 4) Refresh Token -> Redis에 저장
        // Key는 "RT: " + username, 만료시간은 7일
        String redisKey = "RT: " + username;

        redisService.setValues(
            redisKey,
            refreshToken,
            Duration.ofMillis(refreshExpiredMs)
        );

        // redis 저장 값 로그 출력
        String savedValue = redisService.getValues(redisKey);
        System.out.println("Redis 저장 확인 - " + redisKey + ", Value: " + savedValue);

        // 5) 로그인 응답 생성
        return new LoginResponseDto(
            username,
            accessToken,
            refreshToken,
            "로그인 성공"
        );
    }

    public LogoutResponseDto logout(String username, String token) {
        try {
            // Redis에서 Refresh Token 제거
            String refreshTokenKey = "RT: " + username;
            String refreshToken = redisService.getValues(refreshTokenKey);

            if (refreshToken == null) {
                return LogoutResponseDto.fail("이미 로그아웃된 사용자입니다.");
            }

            // Refresh Token 삭제
            redisService.deleteValues(refreshTokenKey);
            log.info("Refresh Token 삭제 완료: {}", username);

            if (token != null && !token.isEmpty()) {
                redisService.setValues(
                    "BL: " + token,
                    "true",
                    Duration.ofMinutes(30)
                );
                log.info("Access Token Blacklist 추가 완료: {}", username);
            }

            return LogoutResponseDto.success();
        } catch (Exception e) {
            log.error("로그아웃 처리 중 에러 발생: {}", e.getMessage());
            return  LogoutResponseDto.fail("로그아웃 처리 중 오류가 발생했습니다.");
        }
    }

    private String extractAccessToken(Authentication authentication) {
        String credentials = (String) authentication.getCredentials();
        if (credentials != null && credentials.startsWith("Bearer ")) {
            return credentials.substring(7);
        }
        return null;
    }

    public boolean checkEmailDuplicate(String email) {
        return memberRepository.existsByEmail(email);
    }
    public boolean checkIdDuplicate(String userId) {
        return memberRepository.existsByUsername(userId);
    }

    public boolean checkPhoneDuplicate(String phone) {
        return memberRepository.existsByPhone(phone);
    }

    public void sendCodeToEmail(String toEmail) {
        this.checkDuplicatedEmail(toEmail);
        String title = "FELESS-Market 이메일 인증 번호";
        String authCode = this.createCode();
        mailService.sendEmail(toEmail, title, authCode);
        // 이메일 인증 요청 시 인증 번호 Redis에 저장 ( key = "AuthCode " + Email / value = AuthCode )
        redisService.setValues(AUTH_CODE_PREFIX + toEmail, authCode, Duration.ofMillis(this.authCodeExpirationMillis));
    }

    private void checkDuplicatedEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            log.debug("MemberServiceImpl.checkDuplicatedEmail exception occur email: {}", email);
            throw new RuntimeException("멤버가 존재합니다.");
        }
    }

    private String createCode() {
        int length = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.debug("MemberService.createCode() exception occur");
            throw new RuntimeException("NO_SUCH_ALGORITHM");
        }
    }

    public EmailVerificationResult verifiedCode(String email, String authCode) {
        this.checkDuplicatedEmail(email);
        String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + email);
        boolean authResult = redisService.checkExistsValue(redisAuthCode) && redisAuthCode.equals(authCode);
        System.out.println("authResult : " + authResult);

        return EmailVerificationResult.of(authResult);
    }

    /*
      [아이디 찾기] 이메일 인증번호 발송
      - 이름과 이메일이 모두 맞는지 확인
      - 인증번호 생성 후 이메일 발송 & Redis 저장
     */
    public void sendCodeToEmailForFind(String name, String toEmail) {
        // 1. 이름, 이메일이 정확히 일치하는 회원이 존재하는지 확인
        checkNameEmailExist(name, toEmail);

        // 2. 인증번호 생성
        String authCode = createCode();

        // 3. 메일 전송
        String title = "[KurlyKelly] 아이디 찾기 인증번호";
        String text = "아래 인증번호를 입력해주세요.\n인증번호: " + authCode;
        mailService.sendEmail(toEmail, title, text);

        // 4. Redis에 인증번호 저장
        redisService.setValues(
            AUTH_CODE_PREFIX + toEmail,
            authCode,
            Duration.ofMillis(this.authCodeExpirationMillis)
        );
    }

    /*
    * [아이디 찾기] 이메일 인증번호 검증
    * - 이름과 이메일이 모두 맞는지 확인
    * - Redis에 저장된 코드와 일치하는지 확인
    */
    public boolean verifyCodeForFindid(String name, String email, String inputCode) {
        // 1. 이름+이메일이 맞는회원이 있는지 다시 확인
        checkNameEmailExist(name, email);
        // 2. Redis에서 인증번호 조회
        String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + email);
        if (redisAuthCode == null) {
            // 만료 or 잘못된 키
            return false;
        }
        if (!redisAuthCode.equals(inputCode)) {
            // 불일치
            return false;
        }

        // 3. 일치하면 인증 성공 -> Redis에서 제거(재사용 방지)
        redisService.deleteValues(AUTH_CODE_PREFIX + email);
        return true;
    }

    /*
    * [아이디 찾기] 인증 성공 후, 실제 username 반환
    *  - 이름과 이메일이 일치하는 회원의 username 가져옴
    */
    public String findUsernameByNameAndEmail(String name, String email) {
        Member member = memberRepository.findByNameAndEmail(name, email)
            .orElseThrow(() -> new RuntimeException("해당 정보로 가입된 회원이 없습니다."));
        return member.getUsername();
    }

    // 1) 이름 + 이메일 일치하는지 확인 후, 인증번호 발송
    public void sendCodeToEmailForResetPw(String name, String toEmail) {
        // 1. 이름, 이메일 일치하는 회원이 존재하는지 확인
        checkNameEmailExist(name, toEmail);

        // 2. 인증번호 생성
        String authCode = createCode();

        // 3. 메일 전송
        String title = "[KurlyKelly] 비밀번호 재설정 인증번호";
        String text = "아래 인증번호를 입력해주세요. \n인증번호 " + authCode;
        mailService.sendEmail(toEmail,title,text);

        // 4. Redis 저장
        redisService.setValues(
            AUTH_CODE_PREFIX + toEmail,
            authCode,
            Duration.ofMillis(this.authCodeExpirationMillis)
        );
    }

    // 2) [비밀번호 찾기]인증번호 검증
    public boolean verifyCodeForResetPw(String name, String email, String inputCode) {
        checkNameEmailExist(name, email);

        String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + email);
        if (redisAuthCode == null) {
            // 만료
            return false;
        }
        if (!redisAuthCode.equals(inputCode)) {
            return false;
        }
        // 일치하면 Redis에서 제거
        redisService.deleteValues(AUTH_CODE_PREFIX + email);
        return true;
    }

    // 3) 인증 성공 후 비밀번호 변경
    public void resetPassword(String name, String email, String newPassword) {
        Member member = memberRepository.findByNameAndEmail(name, email)
            .orElseThrow(() -> new RuntimeException("해당 정보로 가입된 회원이 없습니다."));

        // 새 비밀번호 암호화 후 업데이트
        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }

    /*
    * [아이디 / 비밀번호 재설정]용으로 이름과 이메일이 모두 존재하는지 확인
    * - 둘 중 하나라도 틀리면 예외 발생
    */
    private void checkNameEmailExist(String name, String email) {
        Optional<Member> member = memberRepository.findByNameAndEmail(name, email);
        if(member.isEmpty()) {
            log.debug("MemberService.checkNameEmailExist - 이름 또는 이메일 불일치 name: {}, email:{}", name, email);
            throw new RuntimeException("이름 또는 이메일이 잘못되었습니다.");
        }
    }
}
