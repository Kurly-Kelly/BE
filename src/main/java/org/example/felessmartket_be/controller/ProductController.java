package org.example.felessmartket_be.controller;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.felessmartket_be.domain.MainCategory;
import org.example.felessmartket_be.domain.Product;
import org.example.felessmartket_be.domain.SubCategory;
import org.example.felessmartket_be.domain.dto.productDto.ProductRequestDto;
import org.example.felessmartket_be.domain.dto.productDto.ProductResponseDto;
import org.example.felessmartket_be.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/product")
@RestController
public class ProductController {

    ProductService productService;

    // 특정 상품 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) {
        ProductResponseDto product = productService.getProduct(id);
        return ResponseEntity.ok(product);
    }

    // MainCategory로 상품 목록 조회
    @GetMapping("/main-category/{mainCategory}")
    public ResponseEntity<List<ProductResponseDto>> getProductsByMainCategory(@PathVariable MainCategory mainCategory) {
        List<ProductResponseDto> products = productService.getProductsByMainCategory(mainCategory);
        return ResponseEntity.ok(products);
    }

    // MainCategory와 SubCategory로 상품 목록 조회
    @GetMapping("/category/{subCategory}")
    public ResponseEntity<List<ProductResponseDto>> getProductsByMainAndSubCategory(@PathVariable SubCategory subCategory) {
        List<ProductResponseDto> products = productService.getProductsBySubCategory(subCategory);
        return ResponseEntity.ok(products);
    }


    @GetMapping("")
    public ResponseEntity<List<ProductResponseDto>> findTop5ByOrderByPriceDesc() {
        List<ProductResponseDto> products = productService.findTop5ByOrderByPriceDesc();
        return ResponseEntity.ok(products);
    }


}
