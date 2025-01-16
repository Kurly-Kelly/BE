package org.example.be.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReqeustDto {

    @Schema(example = "test")
    private String username;
    @Schema(example = "test1234")
    private String password;
}
