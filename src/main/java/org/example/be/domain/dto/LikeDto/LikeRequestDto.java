package org.example.be.domain.dto.LikeDto;

import lombok.Data;

@Data
public class LikeRequestDto {
    private String username;
    private Long productId;
}
