package org.example.felessmartket_be.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberRequestDto {

    @NotBlank
    @Size(min = 4, max = 12, message = "ID는 4자 이상 12자 이하로 입력해주세요.")
    private String username;
    @NotBlank
    private String name;
    @NotBlank
    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    private String email;
    @NotBlank
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private String password;
    @NotBlank
    @Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.")
    private String phone;
}
