package org.example.felessmartket_be.controller;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.felessmartket_be.domain.Category;
import org.example.felessmartket_be.domain.Product;
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
@RequestMapping("product")
@RestController
public class ProductController {

    ProductService productService;

    @PostMapping("/getProduct/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @GetMapping("/ParentCategory/{parentCategory}")
    public ResponseEntity<List<ProductResponseDto>> getProductsByParentCategory(
        @PathVariable Category parentCategory) {
        List<ProductResponseDto> products = productService.getProductsByParentCategory(
            parentCategory);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/ChildrenCategory/{ChildrenCategory}")
    public ResponseEntity<List<ProductResponseDto>> getFindByCategory(
        @PathVariable Category ChildrenCategory) {
        List<ProductResponseDto> products = productService.getProductFindByCategory(
            ChildrenCategory);
        return ResponseEntity.ok(products);
    }


//    @GetMapping("/productList")
//    public ResponseEntity<List<Product>> getProductByCategory(
//        @RequestParam(value = "category") Category category) {
//        List<Product> products = productService.getProductByCategory(category);
//        return ResponseEntity.ok(products);
//    }


}
