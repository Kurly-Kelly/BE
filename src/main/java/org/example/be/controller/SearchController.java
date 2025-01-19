package org.example.be.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.be.domain.Product;
import org.example.be.domain.dto.SearchResponseDto;
import org.example.be.domain.dto.searchDto.SearchRequestDto;
import org.example.be.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    // 검색 초기 화면(추천 및 급상승 검색어 화면)
//    @GetMapping("")
//    public ResponseEntity<List<String>> searchPage() {
//        List<String> recommendations = searchService.getRecommendations();
//        return ResponseEntity.ok(recommendations);
//    }

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
    // (3) 다중 필터 적용 (GET + @ModelAttribute)
    //     /search/results/filter?keyword=apple&mainCategories=VEGETABLE&mainCategories=FRUIT&subCategories=LEAF_VEGETABLE&deliveries=EARLY_DELIVERY
    @GetMapping("/results/filter")
    public ResponseEntity<List<SearchResponseDto>> searchProduct(
        @ModelAttribute SearchRequestDto request
    ) {
        List<SearchResponseDto> products = searchService.searchProductWithFilters(request);
        return ResponseEntity.ok(products);
    }

}
