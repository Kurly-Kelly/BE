package org.example.felessmartket_be.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.example.felessmartket_be.domain.Member;
import org.example.felessmartket_be.repository.MemberRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoLoginService {
    private final MemberRepository memberRepository;
    public Member processOAuthPostLogin(String providerId, Map<String, Object> attributes) {
        String provider = "kakao";
        // 기존 사용자 조회(DB 에서 provider 과 providerId 로 사용자 조회)
        return memberRepository.findByProviderAndProviderId(provider, providerId)
            // 사용자가 없으면 새로운 사용자 등록 로직 실행
            .orElseGet(() -> {
                Member.MemberBuilder memberBuilder = Member.builder()
                    .provider(provider)
                    .providerId(providerId);

                // kakao 가 반환한 attributes 맵에서 kakao_account 추출 -> 이메일과 이름
                if (attributes.containsKey("kakao_account")) {
                    Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
                    if (kakaoAccount != null) {
                        memberBuilder.email((String) kakaoAccount.get("email"));

                        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
                        if (profile != null) {
                            memberBuilder.username((String) profile.get("nickname"));
                        }
                    }
                }

                // 빌더로 생성한 member 객체를 DB에 저장하고, 저장된 객체 반환
                return memberRepository.save(memberBuilder.build());
            });
    }
}

//public class KakaoLoginService extends DefaultOAuth2UserService {
//    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
//        OAuth2User oAuth2User = super.loadUser(request);
//        try {
//            System.out.println(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
//
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//
//        return oAuth2User;
//    }
//
//
//}
