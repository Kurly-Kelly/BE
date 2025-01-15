package org.example.felessmartket_be.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LogoutResponseDto {
    private String message;
    private boolean success;

    public static LogoutResponseDto success() {
        return new LogoutResponseDto("로그아웃 성공", true);
    }

    public static LogoutResponseDto fail(String message) {
        return new LogoutResponseDto(message, false);
    }
}
