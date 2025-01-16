package org.example.be.service;

import java.io.IOException;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.be.domain.Product;
import org.example.be.domain.dto.productDto.ProductRequestDto;
import org.example.be.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class AdminService {

    ProductRepository productRepository;
    S3Service s3Service;


    public void saveProductWithImage(
        List<MultipartFile> imageFile, ProductRequestDto productRequestDto) throws IOException {
        // 1. S3에 업로드하고 URL 반환
        List<String> imageUrl = s3Service.uploadFilesToS3(imageFile);

        // 2. 이미지 URL을 Product 엔티티에 저장
        log.info("imageUrl는 뭘까요{}:",imageUrl);
        Product product =  ProductRequestDto.of(productRequestDto);
        product.setImageUrls(imageUrl);

        // 3. DB에 상품 정보 저장
        productRepository.save(product);
    }
}