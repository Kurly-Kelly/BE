package org.example.be.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDeleteResponseDto {

    private boolean success;
    private String message;

    public static MemberDeleteResponseDto success(String message) {
        return new MemberDeleteResponseDto(true, message);
    }

    public static MemberDeleteResponseDto fail(String message) {
        return new MemberDeleteResponseDto(false, message);
    }
}
