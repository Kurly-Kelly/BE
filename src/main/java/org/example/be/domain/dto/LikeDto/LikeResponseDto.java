package org.example.be.domain.dto.LikeDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeResponseDto {
    private boolean success;
    private String message;
    private Long likeCount;
}
