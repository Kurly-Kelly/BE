package org.example.felessmartket_be.controller;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.felessmartket_be.domain.dto.productDto.ProductRequestDto;
import org.example.felessmartket_be.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/admin")
@RestController
public class adminController {

    AdminService adminService;


    @PostMapping("/saveProduct")
    public ResponseEntity<String> saveProduct(
        @ModelAttribute ProductRequestDto productRequestDto, // ProductRequestDto는 ModelAttribute로 받기
        @RequestParam("imgURL") List<MultipartFile> imageFile // 이미지 파일은 RequestParam으로 받기
    ) {
        try {

            adminService.saveProductWithImage(imageFile, productRequestDto);
            // 상품 저장 로직
            return ResponseEntity.ok("상품이 등록되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상품 등록 실패");
        }
    }
}
