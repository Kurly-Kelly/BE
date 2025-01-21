package org.example.be.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.be.domain.Product;
import org.example.be.domain.dto.searchDto.SearchResponseDto;
import org.example.be.domain.dto.searchDto.SearchRequestDto;
import org.example.be.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    // 검색 결과 화면
    @GetMapping("/results")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam("keyword") String keyword) {
        List<Product> products = searchService.searchProductByKeyword(keyword);
        return ResponseEntity.ok(products);
    }

    // 자동완성 검색어
    @GetMapping("/suggestions")
    public ResponseEntity<List<String>> searchSuggestion(
        @RequestParam(value = "keyword", required = false) String keyword) {
        List<String> suggestion = searchService.searchSuggestion(keyword);
        return ResponseEntity.ok(suggestion);
    }

    // 검색 필터
    // 다중 필터 적용 (GET + @ModelAttribute)
    @GetMapping("/results/filter")
    public ResponseEntity<List<Product>> searchFiltered(
        @ModelAttribute SearchRequestDto request
    ) {
        List<Product> result = searchService.searchProductWithFilters(request);
        return ResponseEntity.ok(result);
    }

}
