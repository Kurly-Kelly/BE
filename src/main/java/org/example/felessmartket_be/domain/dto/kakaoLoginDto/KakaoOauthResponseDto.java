package org.example.felessmartket_be.domain.dto.kakaoLoginDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoOauthResponseDto {
    Long id;
    String name;
    String phone;

    public void kakaoInfo(Long id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;

    }



}