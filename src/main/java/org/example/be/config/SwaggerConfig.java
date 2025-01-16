package org.example.be.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    // Swagger UI 접근 경로: http://localhost:8080/swagger-ui/index.html

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .components(new Components()
                .addSecuritySchemes("bearerAuth", createSecurityScheme()))
            .info(info())
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }

    /**
     * Swagger UI 메타데이터 설정
     */
    private Info info() {
        return new Info()
            .title("FelessMarket API")
            .description("ASAC-06 팀 프로젝트 / Market-Kurly 클론 프로젝트")
            .version("1.0");
    }

    /**
     * JWT 인증을 위한 SecurityScheme 설정
     */
    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme()
            .type(SecurityScheme.Type.HTTP) // HTTP 인증 방식
            .scheme("bearer") // Bearer 토큰 방식
            .bearerFormat("JWT") // JWT 토큰 형식
            .description("JWT Bearer 토큰을 입력하세요");
    }
}
