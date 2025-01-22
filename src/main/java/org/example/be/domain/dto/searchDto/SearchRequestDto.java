package org.example.be.domain.dto.searchDto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchRequestDto {
    @Schema(example = "고")
    private String keyword;
    @Schema(example = "ROOT_VEGETABLE")
    private List<String> subCategory;
    @Schema(example = "VEGETABLE")
    private List<String> mainCategory;
    private Integer priceMin;
    private Integer priceMax;
    @Schema(example = "GENERAL_DELIVERY")
    private List<String> delivery;


}
