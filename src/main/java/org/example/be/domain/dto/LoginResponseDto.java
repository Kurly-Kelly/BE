package org.example.be.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDto {
    private String username;
    private String accessToken;
    private String refreshToken;
    private String message;
}
