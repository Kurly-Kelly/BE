package org.example.felessmartket_be.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.felessmartket_be.domain.Member;
import org.example.felessmartket_be.domain.dto.EmailVerificationResult;
import org.example.felessmartket_be.domain.dto.LoginResponseDto;
import org.example.felessmartket_be.domain.dto.LogoutResponseDto;
import org.example.felessmartket_be.domain.dto.MemberRequestDto;
import org.example.felessmartket_be.jwt.JWTUtil;
import org.example.felessmartket_be.repository.MemberRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final long authCodeExpirationMillis = 1800000;

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;


    public Member create(MemberRequestDto memberRequestDto) {
        Member newMember = new Member();
        newMember.setUsername(memberRequestDto.getUsername());
        newMember.setName(memberRequestDto.getName());
        newMember.setEmail(memberRequestDto.getEmail());
        newMember.setPhone(memberRequestDto.getPhone());
        newMember.setPassword(passwordEncoder.encode(memberRequestDto.getPassword()));
        System.out.println(newMember);
        memberRepository.save(newMember);
        return newMember;
    }

    // username, password를 받아서 JWT Access/Refresh 코큰 발급
    public LoginResponseDto login(String username, String password) {
        // 1) DB에서 사용자 정보 조회
        Member member = memberRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

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
        redisService.setValues(
            "RT: " + username,
            refreshToken,
            Duration.ofMillis(refreshExpiredMs)
        );

        // 5) 로그인 응답 생성
        return new LoginResponseDto(
            username,
            accessToken,
            refreshToken,
            "로그인 성공"
        );
    }

    public LogoutResponseDto logout(String username) {
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

            // Access Token 블랙리스트 처리
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                String accessToken = extractAccessToken(authentication);
                if (accessToken != null) {
                    redisService.setValues(
                        "BL: " + accessToken,
                        "true",
                        Duration.ofMinutes(30)
                    );
                    log.info("Access Token Blacklist 추가 완료: {}", username);
                }
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

}
