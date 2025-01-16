package org.example.be.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.be.domain.MainCategory;
import org.example.be.domain.Product;
import org.example.be.domain.ProductStatus;
import org.example.be.domain.SubCategory;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SearchResponseDto {
    Long id;
    String name;
    String description;
    Integer price;
    Integer quantity;
    ProductStatus productStatus;
    MainCategory mainCategory;
    SubCategory subCategory;
    String imgURL;

    public static SearchResponseDto from(Product product) {
        return new SearchResponseDto(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getQuantity(),
            product.getProductStatus(),
            product.getMainCategory(),
            product.getSubCategory(),
            product.getImageUrls().toString()
        );
    }
}
