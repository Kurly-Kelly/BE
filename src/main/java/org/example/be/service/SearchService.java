package org.example.be.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.be.domain.Product;
import org.example.be.domain.dto.productDto.ProductResponseDto;
import org.example.be.domain.dto.searchDto.SearchRequestDto;
import org.example.be.domain.dto.searchDto.SearchSpecification;
import org.example.be.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class SearchService {

    private final ProductRepository productRepository;

    // 검색 초기화면
    // keyword 를 포함한 상품 검색
    public List<ProductResponseDto> searchProductByKeyword(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword)
            .stream()
            .map(ProductResponseDto::fromEntity).toList();
    }

    // keyword 를 포함한 자동완성 검색어 추천
    public List<String> searchSuggestion(String keyword) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(keyword);
        return products.stream()
            .map(Product::getName)
            .collect(Collectors.toList());
    }

    // 검색 필터 옵션
    // "키워드 + 복수 필터" 적용
    public List<ProductResponseDto> searchProductWithFilters(SearchRequestDto request) {
        return productRepository.findAll(SearchSpecification.searchWith(request))
            .stream()
            .map(ProductResponseDto::fromEntity).toList();
    }
}
