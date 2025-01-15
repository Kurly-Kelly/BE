package org.example.felessmartket_be.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3Service {

    private final AmazonS3 amazonS3;
    @Value("${cloud.s3.bucket}")
    private String bucketName;

    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public List<String> uploadFilesToS3(List<MultipartFile> files) throws IOException {
        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();

            // S3에 업로드
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, inputStream, null));

            // 업로드된 파일의 URL을 리스트에 추가
            String fileUrl = amazonS3.getUrl(bucketName, fileName).toString();
            imageUrls.add(fileUrl);
        }

        return imageUrls;
    }
}