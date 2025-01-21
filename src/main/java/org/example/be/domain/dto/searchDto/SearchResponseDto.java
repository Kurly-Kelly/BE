package org.example.be.domain.dto.searchDto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.be.domain.Delivery;
import org.example.be.domain.MainCategory;
import org.example.be.domain.Product;
import org.example.be.domain.ProductStatus;
import org.example.be.domain.SubCategory;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SearchResponseDto {
    Long id;
    String name;
    String description;
    Integer price;
    Integer quantity;
    Delivery delivery;
    ProductStatus productStatus;
    MainCategory mainCategory;
    SubCategory subCategory;
    List<String> imageUrl;

    public static SearchResponseDto from(Product product) {
        return SearchResponseDto.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .quantity(product.getQuantity())
            .productStatus(product.getProductStatus())
            .mainCategory(product.getMainCategory())
            .subCategory(product.getSubCategory())
            .imageUrl(product.getImageUrls())
            .build();
    }
}
