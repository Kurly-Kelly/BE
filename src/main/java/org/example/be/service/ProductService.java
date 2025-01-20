package org.example.be.service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.be.domain.MainCategory;
import org.example.be.domain.Product;
import org.example.be.domain.SubCategory;
import org.example.be.domain.dto.productDto.ProductResponseDto;
import org.example.be.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Page<ProductResponseDto> getProductsByMainCategory(MainCategory mainCategory, PageRequest pageRequest) {
        // 페이지네이션 적용
        Page<Product> productsPage = productRepository.findByMainCategory(mainCategory, pageRequest);

        // 결과가 없다면 예외 처리
        if (productsPage.isEmpty()) {
            throw new IllegalArgumentException("MainCategory에서 상품을 찾을 수 없습니다: " + mainCategory);
        }

        // Page<Product>를 Page<ProductResponseDto>로 변환하여 반환
        return productsPage.map(ProductResponseDto::fromEntity);
    }


    // MainCategory와 SubCategory로 상품 목록 조회
    public Page<ProductResponseDto> getProductsBySubCategory(SubCategory subCategory, PageRequest pageRequest) {
        Page<Product> productsPage = productRepository.findBySubCategory(subCategory, pageRequest);
        if (productsPage.isEmpty()) {
            throw new IllegalArgumentException("sub category 를 찾을 수 없습니다:" + subCategory);
        }
        return productsPage.map(ProductResponseDto::fromEntity);
    }


    public List<ProductResponseDto> findTop10ByOrderByPriceDesc() {
        List<Product> products = productRepository.findTop10ByOrderByPriceDesc();
        return products.stream().map(ProductResponseDto::fromEntity).toList();
    }

//    public List<Product> getRandomProducts() {
//        List<Product> randomProducts = new ArrayList<>();
//        Random random = new Random();
//
//        // productId의 최대값 가져오기
//        Long maxProductId = productRepository.findMaxProductId();
//        if (maxProductId == null || maxProductId == 0) {
//            return randomProducts; // 상품이 없으면 빈 리스트 반환
//        }
//
//        // 랜덤 ID 생성하여 상품 조회
//        for (int i = 0; i < 5\; i++) {
//            Long randomId = 1L + random.nextLong(maxProductId); // 1부터 maxProductId 사이의 랜덤 숫자
//            List<Product> product = productRepository.findByProductId(randomId);
//            if (!product.isEmpty()) {
//                randomProducts.addAll(product);
//            }
//        }
//
//        return randomProducts;
//    }

}