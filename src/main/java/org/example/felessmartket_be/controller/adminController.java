package org.example.felessmartket_be.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.felessmartket_be.domain.dto.productDto.ProductRequestDto;
import org.example.felessmartket_be.domain.dto.productDto.ProductResponseDto;
import org.example.felessmartket_be.service.adminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/admin")
@RestController
public class adminController {

    adminService productService;

    @PostMapping("/saveProduct")
    public ResponseEntity<ProductResponseDto> save(
        @RequestBody ProductRequestDto productRequestDto) {
        try {
            // Base64로 받은 이미지를 처리하여 URL로 저장
            ProductResponseDto responseDto = productService.save(productRequestDto);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // 에러 발생 시 500 반환
        }
    }
}