package org.example.felessmartket_be.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.felessmartket_be.domain.Category;
import org.example.felessmartket_be.domain.Product;
import org.example.felessmartket_be.domain.ProductStatus;

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
    Category category;
    String imgURL;

    public static SearchResponseDto from(Product product) {
        return new SearchResponseDto(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getQuantity(),
            product.getProductStatus(),
            product.getCategory(),
            product.getImgURL()
        );
    }
}
