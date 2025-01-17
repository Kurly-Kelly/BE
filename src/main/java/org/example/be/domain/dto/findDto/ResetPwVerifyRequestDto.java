package org.example.be.domain.dto.findDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ResetPwVerifyRequestDto {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "인증번호를 입력해주세요.")
    private String code;

    @NotBlank(message = "새 비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message ="비밀번호는 8~20자로 입력해주세요.")
    private String newPassword;
}
