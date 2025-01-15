package org.example.felessmartket_be.domain.dto.productDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.felessmartket_be.domain.MainCategory;
import org.example.felessmartket_be.domain.Product;
import org.example.felessmartket_be.domain.ProductStatus;
import org.example.felessmartket_be.domain.SubCategory;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@NoArgsConstructor
public class ProductRequestDto {

    String name;
    String description;
    Integer price;
    Integer quantity;
    ProductStatus productStatus;

    MainCategory mainCategory;
    SubCategory subCategory;


    String imageUrl; // 여러 이미지 URL 저장 가능

    public static Product of(ProductRequestDto productRequestDto) {
        return new Product(
            null,
            productRequestDto.getName(),
            productRequestDto.getDescription(),
            productRequestDto.getPrice(),
            productRequestDto.getQuantity(),
            ProductStatus.available,
            productRequestDto.getMainCategory(),
            productRequestDto.getSubCategory(),
            null
        );
    }
}