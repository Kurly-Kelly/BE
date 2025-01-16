package org.example.be.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.be.domain.MainCategory;
import org.example.be.domain.Product;
import org.example.be.domain.SubCategory;
import org.example.be.domain.dto.productDto.ProductResponseDto;
import org.example.be.repository.ProductRepository;
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
        Product product = productRepository.findByIdWithImages(id)
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
    public List<ProductResponseDto> getProductsBySubCategory(SubCategory subCategory) {
        List<Product> products = productRepository.findBySubCategory(subCategory);
        if (products.isEmpty()) {
            throw new IllegalArgumentException("sub category 를 찾을 수 없습니다:" + subCategory);
        }
        return products.stream()
            .map(ProductResponseDto::fromEntity)
            .toList();
    }

    public List<ProductResponseDto> findTop5ByOrderByPriceDesc() {
        List<Product> products = productRepository.findTop5ByOrderByPriceDesc();
        return products.stream().map(ProductResponseDto::fromEntity).toList();
    }


// 카테고리를 통한 상품 리스트 조회
//    public List<Product> getProductByCategory(Category category) {
//        return productRepository.findByCategoryIgnoreCase(category);
//    }
}