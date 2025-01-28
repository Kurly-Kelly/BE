package org.example.be.domain.dto.productDto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.be.domain.Delivery;
import org.example.be.domain.MainCategory;
import org.example.be.domain.Product;
import org.example.be.domain.ProductStatus;
import org.example.be.domain.SubCategory;

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
    Integer discount;
    ProductStatus productStatus;
    MainCategory mainCategory;
    SubCategory subCategory;
    Delivery delivery;
    List<String> imageUrls; // 여러 이미지 URL 저장 가능


    public static ProductResponseDto fromEntity(Product product) {
        return ProductResponseDto.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .quantity(product.getQuantity())
            .discount(product.getDiscount())
            .delivery(product.getDelivery())
            .productStatus(product.getProductStatus())
            .mainCategory(product.getMainCategory())
            .subCategory(product.getSubCategory())
            .imageUrls(product.getImageUrls())
            .build();
    }
}