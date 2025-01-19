package org.example.be.domain.dto.productDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.be.domain.Delivery;
import org.example.be.domain.MainCategory;
import org.example.be.domain.Product;
import org.example.be.domain.ProductStatus;
import org.example.be.domain.SubCategory;

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
    Delivery delivery;
    MainCategory mainCategory;
    SubCategory subCategory;


    String imageUrl; // 여러 이미지 URL 저장 가능

    public static Product of(ProductRequestDto productRequestDto) {
        return new Product(
            null,
            productRequestDto.getName(),
            productRequestDto.getDescription(),
            productRequestDto.getPrice(),
            productRequestDto.getDelivery(),
            productRequestDto.getQuantity(),
            ProductStatus.available,
            productRequestDto.getMainCategory(),
            productRequestDto.getSubCategory(),
            null
        );
    }
}