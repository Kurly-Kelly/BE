package org.example.be.domain.dto;

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

import java.util.List;

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
    List<String> imageUrls;

    public static SearchResponseDto from(Product product) {
        return new SearchResponseDto(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getQuantity(),
            product.getDelivery(),
            product.getProductStatus(),
            product.getMainCategory(),
            product.getSubCategory(),
            product.getImageUrls()
        );
    }
}
