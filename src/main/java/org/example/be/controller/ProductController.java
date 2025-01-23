package org.example.be.controller;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.be.domain.DiscountStatus;
import org.example.be.domain.MainCategory;
import org.example.be.domain.Product;
import org.example.be.domain.SubCategory;
import org.example.be.domain.dto.productDto.ProductResponseDto;
import org.example.be.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public Page<ProductResponseDto> getProductsByCategory(
            @PathVariable MainCategory mainCategory,
            @RequestParam int page,
            @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return productService.getProductsByMainCategory(mainCategory, pageRequest);
    }

    // MainCategory와 SubCategory로 상품 목록 조회
    @GetMapping("/category/{subCategory}")
    public Page<ProductResponseDto> getProductsByMainAndSubCategory(
            @PathVariable SubCategory subCategory,
            @RequestParam int page,
            @RequestParam int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return productService.getProductsBySubCategory(subCategory, pageRequest);
    }


    @GetMapping("discount/{discountStatus}")
    public ResponseEntity<List<ProductResponseDto>> findTop5ByOrderByPriceDesc(@PathVariable DiscountStatus discountStatus) {
        List<ProductResponseDto> products = productService.findByDiscountStatus(discountStatus);
        return ResponseEntity.ok(products);
    }

//    @GetMapping("/random")
//    public ResponseEntity<List<Product>> getRandomProducts() {
//        List<Product> randomProducts = productService.getRandomProducts();
//        return ResponseEntity.ok(randomProducts);
//    }



}
