package org.example.felessmartket_be.domain.dto.kakaoLoginDto;

// 카카오에서 반환하는 사용자 정보를 매핑하기 위한 Dto

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUserInfoDto {
    private String id;
    private String username;
    private String email;
    private String phone;

}
