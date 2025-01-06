package org.example.felessmartket_be.jwt;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.felessmartket_be.domain.Member;
import org.example.felessmartket_be.repository.MemberRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor

public class JwtAuthentictionFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        // 1) 헤더에서 토큰 추출
        String token = resolveToken(request);

        // 2) 토큰이 존재하고 만료되지 않았다면 검증 진행
        if (token != null) {
            try {
                // 토큰 만료 여부, 위조 여부 등 확인
                if (!jwtUtil.isExpired(token)) {
                    // 2-1) 토큰에서 username(로그인 식별자) 추출
                    String username = jwtUtil.getUsername(token);

                    // 2-2) DB에서 사용자 정보 조회
                    Member member = memberRepository.findByUsername(username).orElse(null);
                    if (member != null) {
                        // 2-3) SecurityContext에 인증 정보 등록
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            // principal (UserDetails 대체)
                            User.withUsername(username).password(member.getPassword()).authorities("ROLE_USER").build(),
                            null,
                            Collections.emptyList()
                        );
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        log.info("[인증 성공] username = {}", username);
                    }
                }
            } catch (JwtException e) {
                log.error("[인증 실패] 잘못된 토큰 사용: {}", e.getMessage());
            } catch (Exception e) {
                log.error("[인증 실패] 에러 발생: {}", e.getMessage());
            }
        }

        // 3) 필터 체인 진행
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
