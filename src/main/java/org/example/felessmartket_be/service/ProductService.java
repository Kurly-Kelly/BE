package org.example.felessmartket_be.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.felessmartket_be.domain.MainCategory;
import org.example.felessmartket_be.domain.Product;
import org.example.felessmartket_be.domain.SubCategory;
import org.example.felessmartket_be.domain.dto.productDto.ProductResponseDto;
import org.example.felessmartket_be.exception.CustomException;
import org.example.felessmartket_be.exception.ExceptionType;
import org.example.felessmartket_be.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
@Transactional
public class ProductService {

    ProductRepository productRepository;

    // 특정 상품 상세 조회
    public ProductResponseDto getProduct(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + id));
        return ProductResponseDto.fromEntity(product);
    }

    // MainCategory로 상품 목록 조회
    public List<ProductResponseDto> getProductsByMainCategory(MainCategory mainCategory) {
        List<Product> products = productRepository.findByMainCategory(mainCategory);
        if (products.isEmpty()) {
            throw new IllegalArgumentException("main category 에서 상품을 찾을 수 없습니다: " + mainCategory);
        }
        return products.stream()
            .map(ProductResponseDto::fromEntity)
            .toList();
    }

    // MainCategory와 SubCategory로 상품 목록 조회
    public List<ProductResponseDto> getProductsByMainAndSubCategory(MainCategory mainCategory, SubCategory subCategory) {
        List<Product> products = productRepository.findByMainCategoryAndSubCategory(mainCategory, subCategory);
        if (products.isEmpty()) {
            throw new IllegalArgumentException("main category를 찾을 수 없습니다: " + mainCategory + " sub category 를 찾을 수 없습니다:" + subCategory);
        }
        return products.stream()
            .map(ProductResponseDto::fromEntity)
            .toList();
    }
}

