package org.example.felessmartket_be.service;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.felessmartket_be.domain.Category;
import org.example.felessmartket_be.domain.Product;
import org.example.felessmartket_be.domain.dto.productDto.ProductResponseDto;
import org.example.felessmartket_be.exception.CustomException;
import org.example.felessmartket_be.exception.ExceptionType;
import org.example.felessmartket_be.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
public class ProductService {

    ProductRepository productRepository;

    public ProductResponseDto getProduct(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new CustomException(ExceptionType.NOT_FIND_PRODUCT));

        return ProductResponseDto.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .quantity(product.getQuantity())
            .productStatus(product.getProductStatus())
            .category(product.getCategory())
            .imgURL(product.getImgURL())
            .build();
    }

    public List<ProductResponseDto> getProductsByParentCategory(Category parentCategory) {
        List<Category> childCategories = Category.getChildCategories(parentCategory);
        List<Product> products = productRepository.findByParentCategory(childCategories);
        return products.stream().map(ProductResponseDto::of).toList();
    }


    public List<ProductResponseDto> getProductFindByCategory(Category parentCategory) {
        List<Product> childCategories = productRepository.findByCategory(parentCategory);
        return childCategories.stream().map(ProductResponseDto::of).toList();

    }


// 카테고리를 통한 상품 리스트 조회
//    public List<Product> getProductByCategory(Category category) {
//        return productRepository.findByCategoryIgnoreCase(category);
//    }
}