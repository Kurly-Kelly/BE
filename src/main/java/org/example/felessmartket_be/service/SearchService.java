package org.example.felessmartket_be.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.felessmartket_be.domain.Product;
import org.example.felessmartket_be.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final ProductRepository productRepository;

    // 검색 초기화면
    // keyword 를 포함한 상품 검색
    public List<Product> searchProductByKeyword(String keyword){
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    // keyword 를 포함한 자동완성 검색어 추천
    public List<String> searchSuggestion(String keyword){
        List<Product> products = productRepository.findByNameContainingIgnoreCase(keyword);
        return products.stream()
            .map(Product::getName)
            .collect(Collectors.toList());
    }


}