package org.example.felessmartket_be.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.example.felessmartket_be.domain.Member;
import org.example.felessmartket_be.service.KakaoLoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KakaoLoginConrtoller {
    private final KakaoLoginService kakaoLoginService;

    // 카카오 로그인 성공 시
    @GetMapping("/oauth2/code/kakao")
    public ResponseEntity<?> loginSuccess(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.badRequest().body("Authentication principal is null");
        }

        // 카카오로부터 인증된 사용자의 정보가 담긴 속성(Attributes) 맵을 가져옴
        Map<String, Object> attributes = principal.getAttributes();
        // Kakao 사용자 ID는 일반적으로 attributes의 "id" 필드에 있음
        // 카카오 사용자 아이디 추출
        String providerId = String.valueOf(attributes.get("id"));

        Member member = kakaoLoginService.processOAuthPostLogin(providerId, attributes);
        return ResponseEntity.ok(member);
    }

}
