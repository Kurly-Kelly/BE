package org.example.felessmartket_be.domain.dto.productDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.felessmartket_be.domain.MainCategory;
import org.example.felessmartket_be.domain.Product;
import org.example.felessmartket_be.domain.ProductStatus;
import org.example.felessmartket_be.domain.SubCategory;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponseDto {

    Long id;
    String name;
    String description;
    Integer price;
    Integer quantity;
    ProductStatus productStatus;
    MainCategory mainCategory;
    SubCategory subCategory;
    @JsonProperty("image")
    String imgURL;


    public static ProductResponseDto fromEntity(Product product) {
        return ProductResponseDto.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .quantity(product.getQuantity())
            .productStatus(product.getProductStatus())
            .mainCategory(product.getMainCategory())
            .subCategory(product.getSubCategory())
            .imgURL(product.getImgURL())
            .build();
    }
}
