package org.example.felessmartket_be.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.felessmartket_be.domain.Product;
import org.example.felessmartket_be.domain.dto.productDto.ProductRequestDto;
import org.example.felessmartket_be.domain.dto.productDto.ProductResponseDto;
import org.example.felessmartket_be.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class ProductService {

    ProductRepository productRepository;

    public ProductResponseDto save(ProductRequestDto productRequestDto) throws IOException {

        String imageUrl = saveImageFromBase64(productRequestDto.getImgURL());

        Product product = ProductRequestDto.of(productRequestDto);
        product.setImgURL(imageUrl);
        log.info("imageUrl {}:", imageUrl); //로그 추가

        Product savedProduct = productRepository.save(product);

        return new ProductResponseDto(savedProduct.getId());
    }

    private String saveImageFromBase64(String base64Image) throws IOException {
        if (base64Image == null || base64Image.isEmpty()) {
            return null;
        }

        // Base64로 인코딩된 이미지에서 "data:image/jpeg;base64," 부분을 제거
        String base64String = base64Image.split(",")[1];

        // Base64로 디코딩하여 바이트 배열로 변환
        byte[] imageBytes = Base64.getDecoder().decode(base64String);

        // 파일명 생성 (UUID 사용)
        String uploadDir = "uploads/";
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs(); // 디렉토리가 없으면 생성
        }

        // 고유한 파일 이름 생성
        String uniqueFilename = UUID.randomUUID().toString() + ".jpg";  // 확장자는 이미지 형식에 맞게 설정
        File destination = new File(uploadPath, uniqueFilename);

        // 파일로 저장
        try (FileOutputStream fileOutputStream = new FileOutputStream(destination)) {
            fileOutputStream.write(imageBytes);
        }

        // 저장된 파일의 URL 반환 (URL 경로 예: "/uploads/파일명")
        log.info("Saved image URL: " + "/uploads/" + uniqueFilename);  // 로그 추가
        return "/uploads/" + uniqueFilename;

        //추후 MultipartFile 방식으로 전환
    }

    // 카테고리를 통한 상품 리스트 조회
    public List<Product> getProductByCategory(String category){
        return productRepository.findByCategoryIgnoreCase(category);
    }
}