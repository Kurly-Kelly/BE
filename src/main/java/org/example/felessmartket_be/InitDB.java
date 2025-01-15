package org.example.felessmartket_be;

import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.felessmartket_be.domain.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitDB implements CommandLineRunner {

    private final InitService initService;

    @Override
    public void run(String... args) {
        initService.initializeData();
    }

    @Component
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        @Transactional
        public void initializeData() {

            List<String> imgUrl1 = new ArrayList<>();
            imgUrl1.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A9%E1%84%80%E1%85%AE%E1%84%86%E1%85%A11.jpg");
            imgUrl1.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A9%E1%84%80%E1%85%AE%E1%84%86%E1%85%A11-9.png");
            imgUrl1.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A9%E1%84%80%E1%85%AE%E1%84%86%E1%85%A11-11.webp");

            List<String> imgUrl2 = new ArrayList<>();
            imgUrl2.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A1%E1%86%B7%E1%84%8C%E1%85%A1%E1%84%86%E1%85%A6%E1%84%8B%E1%85%B5%E1%86%AB.avif");
            imgUrl2.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A1%E1%86%B7%E1%84%8C%E1%85%A1-2.avif");
            imgUrl2.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A1%E1%86%B7%E1%84%8C%E1%85%A1-2.png");
            imgUrl2.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A1%E1%86%B7%E1%84%8C%E1%85%A1-3.png");
            imgUrl2.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A1%E1%86%B7%E1%84%8C%E1%85%A1-5.png");
            imgUrl2.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A1%E1%86%B7%E1%84%8C%E1%85%A1-6.png");
            imgUrl2.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A1%E1%86%B7%E1%84%8C%E1%85%A1-7.png");
            imgUrl2.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A1%E1%86%B7%E1%84%8C%E1%85%A1-8.png");
            imgUrl2.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB.webp");

            List<String> imgUrl3 = new ArrayList<>();
            imgUrl3.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB.avif");
            imgUrl3.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB2.avif");
            imgUrl3.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB8.png");
            imgUrl3.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB.webp");


            List<String> imgUrl4 = new ArrayList<>();
            imgUrl4.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB%E1%84%8C%E1%85%AE%E1%84%89%E1%85%B3%E1%84%86%E1%85%A6%E1%84%8B%E1%85%B5%E1%86%AB.avif");
            imgUrl4.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB1.avif");
            imgUrl4.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB2.jpg");
            imgUrl4.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB3.jpg");
            imgUrl4.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB4.jpg");

            List<String> imgUrl5 = new ArrayList<>();
            imgUrl5.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB%E1%84%8C%E1%85%AE%E1%84%89%E1%85%B3%E1%84%86%E1%85%A6%E1%84%8B%E1%85%B5%E1%86%AB.avif");
            imgUrl5.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB%E1%84%85%E1%85%A1%E1%84%91%E1%85%A6%E1%84%91%E1%85%B3%E1%84%85%E1%85%A1%E1%86%BC%E1%84%89%E1%85%B3%E1%84%89%E1%85%B5%E1%86%A8%E1%84%89%E1%85%A2%E1%86%AF%E1%84%85%E1%85%A5%E1%84%83%E1%85%B31.png");
            imgUrl5.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB%E1%84%85%E1%85%A1%E1%84%91%E1%85%A6%E1%84%91%E1%85%B3%E1%84%85%E1%85%A1%E1%86%BC%E1%84%89%E1%85%B3%E1%84%89%E1%85%B5%E1%86%A8%E1%84%89%E1%85%A2%E1%86%AF%E1%84%85%E1%85%A5%E1%84%83%E1%85%B32.png");
            imgUrl5.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB%E1%84%85%E1%85%A1%E1%84%91%E1%85%A6%E1%84%91%E1%85%B3%E1%84%85%E1%85%A1%E1%86%BC%E1%84%89%E1%85%B3%E1%84%89%E1%85%B5%E1%86%A8%E1%84%89%E1%85%A2%E1%86%AF%E1%84%85%E1%85%A5%E1%84%83%E1%85%B34.jpg");
            imgUrl5.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB%E1%84%85%E1%85%A1%E1%84%91%E1%85%A6%E1%84%91%E1%85%B3%E1%84%85%E1%85%A1%E1%86%BC%E1%84%89%E1%85%B3%E1%84%89%E1%85%B5%E1%86%A8%E1%84%89%E1%85%A2%E1%86%AF%E1%84%85%E1%85%A5%E1%84%83%E1%85%B35.jpg");
            imgUrl5.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB%E1%84%85%E1%85%A1%E1%84%91%E1%85%A6%E1%84%91%E1%85%B3%E1%84%85%E1%85%A1%E1%86%BC%E1%84%89%E1%85%B3%E1%84%89%E1%85%B5%E1%86%A8%E1%84%89%E1%85%A2%E1%86%AF%E1%84%85%E1%85%A5%E1%84%83%E1%85%B36.jpg");
            imgUrl5.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB%E1%84%85%E1%85%A1%E1%84%91%E1%85%A6%E1%84%91%E1%85%B3%E1%84%85%E1%85%A1%E1%86%BC%E1%84%89%E1%85%B3%E1%84%89%E1%85%B5%E1%86%A8%E1%84%89%E1%85%A2%E1%86%AF%E1%84%85%E1%85%A5%E1%84%83%E1%85%B37.jpg");
            imgUrl5.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB%E1%84%89%E1%85%A2%E1%86%AF%E1%84%85%E1%85%A5%E1%84%83%E1%85%B39.png");
            imgUrl5.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB%E1%84%89%E1%85%A2%E1%86%AF%E1%84%85%E1%85%A5%E1%84%83%E1%85%B310.png");
            imgUrl5.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB%E1%84%89%E1%85%A2%E1%86%AF%E1%84%85%E1%85%A5%E1%84%83%E1%85%B311.png");


            List<String> imgUrl6 = new ArrayList<>();
            imgUrl6.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%A1%E1%86%AB%E1%84%8C%E1%85%B5%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB%E1%84%86%E1%85%A6%E1%84%8B%E1%85%B5%E1%86%AB.avif");
            imgUrl6.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%A1%E1%86%AB%E1%84%8C%E1%85%B5%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB1.avif");
            imgUrl6.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%A1%E1%86%AB%E1%84%8C%E1%85%B5%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB2.jpg");


            List<String> imgUrl7 = new ArrayList<>();
            imgUrl7.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%87%E1%85%A1%E1%86%AB%E1%84%80%E1%85%A5%E1%86%AB%E1%84%8C%E1%85%A9%E1%84%89%E1%85%B3%E1%84%8B%E1%85%B1%E1%84%90%E1%85%B3%E1%84%80%E1%85%A9%E1%84%80%E1%85%AE%E1%84%86%E1%85%A1%E1%84%86%E1%85%A6%E1%84%8B%E1%85%B5%E1%86%AB.jpg");
            imgUrl7.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%87%E1%85%A1%E1%86%AB%E1%84%80%E1%85%A5%E1%86%AB%E1%84%8C%E1%85%A9%E1%84%89%E1%85%B3%E1%84%8B%E1%85%B1%E1%84%90%E1%85%B3%E1%84%80%E1%85%A9%E1%84%80%E1%85%AE%E1%84%86%E1%85%A11.avif");
            imgUrl7.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%87%E1%85%A1%E1%86%AB%E1%84%80%E1%85%A5%E1%86%AB%E1%84%8C%E1%85%A9%E1%84%89%E1%85%B3%E1%84%8B%E1%85%B1%E1%84%90%E1%85%B3%E1%84%80%E1%85%A9%E1%84%80%E1%85%AE%E1%84%86%E1%85%A12.webp");

            List<String> imgUrl8 = new ArrayList<>();
            imgUrl8.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%AA%E1%86%BC%E1%84%89%E1%85%A5%E1%86%BC%E1%84%8C%E1%85%AE%E1%84%87%E1%85%A1%E1%86%A8%E1%84%89%E1%85%A1%E1%84%86%E1%85%A6%E1%84%8B%E1%85%B5%E1%86%AB.jpg");
            imgUrl8.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%AA%E1%86%BC%E1%84%89%E1%85%A5%E1%86%BC%E1%84%8C%E1%85%AE%E1%84%87%E1%85%A1%E1%86%A8%E1%84%89%E1%85%A11.avif");
            imgUrl8.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%AA%E1%86%BC%E1%84%89%E1%85%A5%E1%86%BC%E1%84%8C%E1%85%AE%E1%84%87%E1%85%A1%E1%86%A8%E1%84%89%1%85%A13.jpg");
            imgUrl8.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%AA%E1%86%BC%E1%84%89%E1%85%A5%E1%86%BC%E1%84%8C%E1%85%AE%E1%84%87%E1%85%A1%E1%86%A8%E1%84%89%E1%85%A14.jpg");

            List<String> imgUrl9 = new ArrayList<>();
            imgUrl9.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%B5%E1%86%AB%E1%84%80%E1%85%B5%E1%84%80%E1%85%A1%E1%86%AB%E1%84%89%E1%85%B5%E1%86%A8%E1%84%80%E1%85%A1%E1%86%B7%E1%84%8C%E1%85%A1%E1%84%89%E1%85%B3%E1%84%82%E1%85%A2%E1%86%A8%E1%84%86%E1%85%A6%E1%84%8B%E1%85%B5%E1%86%AB.jpeg");
            imgUrl9.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A1%E1%86%B7%E1%84%8C%E1%85%A1%E1%84%89%E1%85%B3%E1%84%82%E1%85%A2%E1%86%A81.avif");
            imgUrl9.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A1%E1%86%B7%E1%84%8C%E1%85%A1%E1%84%89%E1%85%B3%E1%84%82%E1%85%A2%E1%86%A82.jpeg");
            imgUrl9.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A1%E1%86%B7%E1%84%8C%E1%85%A1%E1%84%89%E1%85%B3%E1%84%82%E1%85%A2%E1%86%A83.png");
            imgUrl9.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A1%E1%86%B7%E1%84%8C%E1%85%A1%E1%84%89%E1%85%B3%E1%84%82%E1%85%A2%E1%86%A84%5C.png");


            List<String> imgUrl10 = new ArrayList<>();
            imgUrl10.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B5%E1%84%86%E1%85%A6%E1%84%8B%E1%85%B5%E1%86%AB.jpg");
            imgUrl10.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B51.jpg");
            imgUrl10.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B52.jpg");
            imgUrl10.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B53.jpg");
            imgUrl10.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/4.jpg");
            imgUrl10.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B55.jpg");
            imgUrl10.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B56.jpg");
            imgUrl10.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B57.jpg");
            imgUrl10.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B59.jpg");
            imgUrl10.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B510.jpg");


            List<String> imgUrl11= new ArrayList<>();
            imgUrl11.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B5+%E1%84%91%E1%85%A6%E1%84%90%E1%85%AE%E1%84%8E%E1%85%B5%E1%84%82%E1%85%A61.jpg");
            imgUrl11.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B5+%E1%84%91%E1%85%A6%E1%84%90%E1%85%AE%E1%84%8E%E1%85%B5%E1%84%82%E1%85%A62.jpg");
            imgUrl11.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B5+%E1%84%91%E1%85%A6%E1%84%90%E1%85%AE%E1%84%8E%E1%85%B5%E1%84%82%E1%85%A63.jpg");
            imgUrl11.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B5+%E1%84%91%E1%85%A6%E1%84%90%E1%85%AE%E1%84%8E%E1%85%B5%E1%84%82%E1%85%A64.jpg");
            imgUrl11.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B5+%E1%84%91%E1%85%A6%E1%84%90%E1%85%AE%E1%84%8E%E1%85%B5%E1%84%82%E1%85%A65.jpg");


            List<String> imgUrl12= new ArrayList<>();
            imgUrl12.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%B5%E1%86%BC%E1%84%89%E1%85%B5%E1%86%BC%E1%84%92%E1%85%A1%E1%86%AB%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B5%E1%84%86%E1%85%A6%E1%84%8B%E1%85%B5%E1%86%AB.jpg");
            imgUrl12.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%B5%E1%86%BC%E1%84%89%E1%85%B5%E1%86%BC%E1%84%92%E1%85%A1%E1%86%AB%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B51.avif");
            imgUrl12.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%B5%E1%86%BC%E1%84%89%E1%85%B5%E1%86%BC%E1%84%92%E1%85%A1%E1%86%AB%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B52.jpg");
            imgUrl12.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%B5%E1%86%BC%E1%84%89%E1%85%B5%E1%86%BC%E1%84%92%E1%85%A1%E1%86%AB%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B53.jpg");


            List<String> imgUrl13= new ArrayList<>();
            imgUrl13.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%86%E1%85%AE%E1%84%82%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%A3%E1%86%A8%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B5%E1%84%86%E1%85%A6%E1%84%8B%E1%85%B5%E1%86%AB.jpg");
            imgUrl13.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%86%E1%85%AE%E1%84%82%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%A3%E1%86%A8%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B51.avif");
            imgUrl13.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%86%E1%85%AE%E1%84%82%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%A3%E1%86%A8%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B52.jpg");
            imgUrl13.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%86%E1%85%AE%E1%84%82%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%A3%E1%86%A8%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B53.jpg");
            imgUrl13.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%86%E1%85%AE%E1%84%82%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%A3%E1%86%A8%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B54.jpg");
            imgUrl13.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%86%E1%85%AE%E1%84%82%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%A3%E1%86%A8%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B55.jpg");
            imgUrl13.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%86%E1%85%AE%E1%84%82%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%A3%E1%86%A8%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B56.jpg");
            imgUrl13.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%86%E1%85%AE%E1%84%82%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%A3%E1%86%A8%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B57.jpg");
            imgUrl13.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%86%E1%85%AE%E1%84%82%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%A3%E1%86%A8%E1%84%89%E1%85%B5%E1%84%80%E1%85%B3%E1%86%B7%E1%84%8E%E1%85%B57.jpg");

            List<String> imgUrl14 = new ArrayList<>();
            imgUrl14.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%82%E1%85%A1%E1%84%86%E1%85%AE%E1%86%AF%E1%84%86%E1%85%A6%E1%84%8B%E1%85%B5%E1%86%AB.jpg");
            imgUrl14.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%82%E1%85%A1%E1%84%86%E1%85%AE%E1%86%AF1.jpg");
            imgUrl14.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%82%E1%85%A1%E1%84%86%E1%85%AE%E1%86%AF2.jpg");
            imgUrl14.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%82%E1%85%A1%E1%84%86%E1%85%AE%E1%86%AF3.jpg");
            imgUrl14.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%82%E1%85%A1%E1%84%86%E1%85%AE%E1%86%AF4.jpg");
            imgUrl14.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%82%E1%85%A1%E1%84%86%E1%85%AE%E1%86%AF5.avif");
            imgUrl14.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%82%E1%85%A1%E1%84%86%E1%85%AE%E1%86%AF6.jpg");
            imgUrl14.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%82%E1%85%A1%E1%84%86%E1%85%AE%E1%86%AF8.jpg");
            imgUrl14.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%82%E1%85%A1%E1%84%86%E1%85%AE%E1%86%AF9.jpg");

            List<String> imgUrl15 = new ArrayList<>();
            imgUrl15.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%A9%E1%84%8B%E1%85%B5%E1%84%86%E1%85%A6%E1%84%8B%E1%85%B5%E1%86%AB.avif");
            imgUrl15.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%A9%E1%84%8B%E1%85%B51.avif");
            imgUrl15.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%A9%E1%84%8B%E1%85%B52.avif");
            imgUrl15.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%A9%E1%84%8B%E1%85%B53.avif");
            imgUrl15.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%A9%E1%84%8B%E1%85%B54.avif");
            imgUrl15.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%A9%E1%84%8B%E1%85%B55.avif");
            imgUrl15.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%A9%E1%84%8B%E1%85%B56.avif");
            imgUrl15.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%A9%E1%84%8B%E1%85%B57.avif");
            imgUrl15.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%A9%E1%84%8B%E1%85%B58.webp");
            imgUrl15.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%A9%E1%84%8B%E1%85%B59.avif");
            imgUrl15.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%A9%E1%84%8B%E1%85%B510.gif");






            List<String> imgUrl16 = new ArrayList<>();
            imgUrl16.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%A9%E1%84%8B%E1%85%B51-1.avif");
            imgUrl16.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%A9%E1%84%8B%E1%85%B51-2.avif");
            imgUrl16.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%A9%E1%84%8B%E1%85%B51-3.jpg");
            imgUrl16.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%A9%E1%84%8B%E1%85%B51-4.jpg");






            List<String> imgUrl17 = new ArrayList<>();
            imgUrl17.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%A9%E1%84%87%E1%85%A1%E1%86%A81.jpg");
            imgUrl17.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%A9%E1%84%87%E1%85%A1%E1%86%A82.avif");
            imgUrl17.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%A9%E1%84%87%E1%85%A1%E1%86%A83.jpg");
            imgUrl17.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%A9%E1%84%87%E1%85%A1%E1%86%A84.jpg");
            imgUrl17.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%A9%E1%84%87%E1%85%A1%E1%86%A85.gif");
            imgUrl17.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%A9%E1%84%87%E1%85%A1%E1%86%A86.jpg");
            imgUrl17.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%A9%E1%84%87%E1%85%A1%E1%86%A87.jpg");
            imgUrl17.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%A9%E1%84%87%E1%85%A1%E1%86%A88.jpg");
            imgUrl17.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%A9%E1%84%87%E1%85%A1%E1%86%A89.jpg");
            imgUrl17.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%A9%E1%84%87%E1%85%A1%E1%86%A810.jpg");





                    List<String> imgUrl18 = new ArrayList<>();
            imgUrl18.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%A9%E1%84%87%E1%85%A1%E1%86%A82-1.jpg");
            imgUrl18.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%A9%E1%84%87%E1%85%A1%E1%86%A82-2.gif");
            imgUrl18.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%A9%E1%84%87%E1%85%A1%E1%86%A82-3.jpg");
            imgUrl18.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%A9%E1%84%87%E1%85%A1%E1%86%A82-3.png");
            imgUrl18.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%A9%E1%84%87%E1%85%A1%E1%86%A82-4.png");




                List<String> imgUrl19 = new ArrayList<>();
            imgUrl19.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A9%E1%84%8E%E1%85%AE1.jpg");
            imgUrl19.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A9%E1%84%8E%E1%85%AE2.avif");
            imgUrl19.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A9%E1%84%8E%E1%85%AE3.jpg");
            imgUrl19.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A9%E1%84%8E%E1%85%AE4.jpg");







                List<String> imgUrl20 = new ArrayList<>();
            imgUrl20.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A9%E1%84%8E%E1%85%AE2-1.jpg");
            imgUrl20.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A9%E1%84%8E%E1%85%AE2-2.jpg");






                List<String> imgUrl21 = new ArrayList<>();
            imgUrl21.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%A1%E1%84%80%E1%85%AA1.avif");
            imgUrl21.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%A1%E1%84%80%E1%85%AA2.webp");





                    List<String> imgUrl22 = new ArrayList<>();
            imgUrl22.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%A1%E1%84%80%E1%85%AA2-1.jpeg");
            imgUrl22.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%A1%E1%84%80%E1%85%AA2-2.avif");
            imgUrl22.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%A1%E1%84%80%E1%85%AA2-3.jpg");
            imgUrl22.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%A1%E1%84%80%E1%85%AA2-4.jpg");




List<String> imgUrl23 = new ArrayList<>();
            imgUrl23.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%87%E1%85%A21.jpg");
            imgUrl23.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%87%E1%85%A22.jpg");






                List<String> imgUrl24 = new ArrayList<>();
            imgUrl24.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%87%E1%85%A22-1.jpg");
            imgUrl24.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%87%E1%85%A22-2.jpg");





                List<String> imgUrl25 = new ArrayList<>();
            imgUrl25.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A1%E1%86%B7%E1%84%80%E1%85%B2%E1%86%AF1.avif");
            imgUrl25.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A1%E1%86%B7%E1%84%80%E1%85%B2%E1%86%AF2.jpg");




                List<String> imgUrl27 = new ArrayList<>();
            imgUrl27.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%A2%E1%86%BC%E1%84%89%E1%85%A5%E1%86%BC1.jpg");
            imgUrl27.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%A2%E1%86%BC%E1%84%89%E1%85%A5%E1%86%AB2.avif");
            imgUrl27.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%A2%E1%86%BC%E1%84%89%E1%85%A5%E1%86%AB3.avif");
            imgUrl27.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%A2%E1%86%BC%E1%84%89%E1%85%A5%E1%86%AB4.avif");
            imgUrl27.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%A2%E1%86%BC%E1%84%89%E1%85%A5%E1%86%AB5.avif");
            imgUrl27.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%A2%E1%86%BC%E1%84%89%E1%85%A5%E1%86%AB6.jpg");
            imgUrl27.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%A2%E1%86%BC%E1%84%89%E1%85%A5%E1%86%AB8.jpg");
            imgUrl27.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%A2%E1%86%BC%E1%84%89%E1%85%A5%E1%86%AB10.avif");
            imgUrl27.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%A2%E1%86%BC%E1%84%89%E1%85%A5%E1%86%AB11.avif");





                List<String> imgUrl26 = new ArrayList<>();
            imgUrl26.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A1%E1%86%B7%E1%84%80%E1%85%B2%E1%86%AF2-1.jpg");
            imgUrl26.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A1%E1%86%B7%E1%84%80%E1%85%B2%E1%86%AF2-2.jpg");
            imgUrl26.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A1%E1%86%B7%E1%84%80%E1%85%B2%E1%86%AF2-4.jpg");
            imgUrl26.add("https://marketkurry-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A1%E1%86%B7%E1%84%80%E1%85%B2%E1%86%AF2-5.jpg");






            Product product1 = createProduct("해남 꿀고구마 베니하루카",7900,"해남 꿀고구마 베니하루카 2/3/5/10kg",45,MainCategory.VEGETABLE,SubCategory.ROOT_VEGETABLE,imgUrl1);
            Product product2 = createProduct("포슬포슬 햇 수미감자",10000,"포슬포슬 햇 수미감자 3kg/5kg/10kg(국내산)",45,MainCategory.VEGETABLE,SubCategory.ROOT_VEGETABLE,imgUrl2);
            Product product3 = createProduct("제주 구좌 당근",5900,"제주 구좌 당근 2/3/5kg (국내산)",45,MainCategory.VEGETABLE,SubCategory.ROOT_VEGETABLE,imgUrl3);
            Product product4 = createProduct("매일야채 고농축 당근의힘",23900,"매일야채 고농축 당근의힘 125ml*24팩",45,MainCategory.VEGETABLE,SubCategory.ROOT_VEGETABLE,imgUrl4);
            Product product5 = createProduct("당근 라페 프랑스식 샐러드",9900,"당근 라페 프랑스식 샐러드 200g",45,MainCategory.VEGETABLE,SubCategory.ROOT_VEGETABLE,imgUrl5);
            Product product6 = createProduct("산지직송 제주 구좌 햇 당근 흙당근",23900,"산지직송 제주 구좌 햇 당근 흙당근 국산 국내산 제철 5kg 10kg 주스용 요리용 업소용",45,MainCategory.VEGETABLE,SubCategory.ROOT_VEGETABLE,imgUrl6);
            Product product7 = createProduct("반건조 스위트 고구마 70g",27900,"",45,MainCategory.VEGETABLE,SubCategory.ROOT_VEGETABLE,imgUrl7);
            Product product8 = createProduct("황성주박사의 국산콩 두유 검은콩 고구마",27900,"황성주박사의 국산콩 두유 검은콩 고구마 190ml 48개",45,MainCategory.VEGETABLE,SubCategory.ROOT_VEGETABLE,imgUrl8);
            Product product9 = createProduct("인기간식 감자스낵 과자세트",19900,"",45,MainCategory.VEGETABLE,SubCategory.ROOT_VEGETABLE,imgUrl9);
            Product product10 = createProduct("해풍월동 시금치 씨앗 채소 텃밭 가꾸기",3000,"",45,MainCategory.VEGETABLE,SubCategory.LEAF_VEGETABLE,imgUrl10);
            Product product11 = createProduct("시금치 페투치네 파스타 페투치니 면",5300,"시금치 페투치네 파스타 페투치니 면",45,MainCategory.VEGETABLE,SubCategory.LEAF_VEGETABLE,imgUrl11);
            Product product12 = createProduct("국내산 싱싱한 시금치",9900,"국내산 싱싱한 시금치 1kg 외",45,MainCategory.VEGETABLE,SubCategory.LEAF_VEGETABLE,imgUrl12);
            Product product13 = createProduct("무농약 모듬 쌈채소 샐러드",13900,"무농약 모듬 쌈채소 샐러드 9종내외 500g 1kg(당일수확)",45,MainCategory.VEGETABLE,SubCategory.LEAF_VEGETABLE,imgUrl13);
            Product product14 = createProduct("콩나물 시루 재배기",13900,"[5%쿠폰]콩나물 시루 재배기_2sizes",45,MainCategory.VEGETABLE,SubCategory.LEAF_VEGETABLE,imgUrl14);

            Product product15 = createProduct(" 올스텐 만능채칼 양배추",4900,"올스텐 만능채칼 양배추 오이 감자 당근 우엉 쏨땀 필러",45,MainCategory.VEGETABLE,SubCategory.LEAF_VEGETABLE,imgUrl15);
            Product product16 = createProduct(" 국내산 간편한 미니오이",13900,"국내산 간편한 미니오이 1kg 외 ",45,MainCategory.VEGETABLE,SubCategory.LEAF_VEGETABLE,imgUrl16);
            Product product17 = createProduct("달콤한 영암 신품종 호박고구마",13900,"달콤한 영암 신품종 호박고구마 호풍미 고구마 세척 3kg 5kg 당근고구마",15900,MainCategory.VEGETABLE,SubCategory.LEAF_VEGETABLE,imgUrl17);
            Product product18 = createProduct("해남 꿀밤 고구마 베니하루카",13900,"해남 꿀밤 고구마 베니하루카 [세척] 3kg 5kg 10kg 20kg 황금호박 고구마",5700,MainCategory.VEGETABLE,SubCategory.LEAF_VEGETABLE,imgUrl18);
            Product product19 = createProduct("고추참치",36100,"고추참치 250g*10개",45,MainCategory.VEGETABLE,SubCategory.LEAF_VEGETABLE,imgUrl19);
            Product product20 = createProduct("금빛 태양의맛 고추가루 매운맛 베트남산",22900,"금빛 태양의맛 고추가루 매운맛 베트남산 1kg ",45,MainCategory.VEGETABLE,SubCategory.LEAF_VEGETABLE,imgUrl20);
            Product product21 = createProduct("경북 부사 꿀사과 못난이",10900,"[경북 부사 꿀사과 못난이 2/3/5kg 택1",45,MainCategory.VEGETABLE,SubCategory.LEAF_VEGETABLE,imgUrl21);
            Product product22 = createProduct("콩나물 시루 재배기",13900,"[5%쿠폰]콩나물 시루 재배기_2sizes",45,MainCategory.VEGETABLE,SubCategory.LEAF_VEGETABLE,imgUrl22);
            Product product23 = createProduct("국산 도라지배즙 배즙 1박스",17900,"국산 도라지배즙 배즙 1박스 30포 ",45,MainCategory.VEGETABLE,SubCategory.LEAF_VEGETABLE,imgUrl23);
            Product product24 = createProduct("짱구는 못말려 배도라지스틱",14900,"짱구는 못말려 배도라지스틱 20포  ",45,MainCategory.VEGETABLE,SubCategory.LEAF_VEGETABLE,imgUrl24);
            Product product25 = createProduct(" 제주도 산지직송 새콤달콤 노지감귤",11900,"제주도 산지직송 새콤달콤 노지감귤 4.5kg 9kg",45,MainCategory.VEGETABLE,SubCategory.LEAF_VEGETABLE,imgUrl25);
            Product product26 = createProduct(" 산지직송 무농약 친환경 제주 감귤",95900," 산지직송 무농약 친환경 제주 감귤 로얄과 10kg ",45,MainCategory.VEGETABLE,SubCategory.LEAF_VEGETABLE,imgUrl26);
            Product product27 = createProduct("  제주 생선 3종", 36900," 제주 생선 3종, 법성포 굴비 골라담기",45,MainCategory.VEGETABLE,SubCategory.LEAF_VEGETABLE,imgUrl27);




            em.persist(product1);
            em.persist(product2);
            em.persist(product3);
            em.persist(product4);
            em.persist(product5);
            em.persist(product6);
            em.persist(product7);
            em.persist(product8);
            em.persist(product9);
            em.persist(product10);
            em.persist(product11);
            em.persist(product12);
            em.persist(product13);
            em.persist(product14);
            em.persist(product15);
            em.persist(product16);
            em.persist(product17);
            em.persist(product18);
            em.persist(product19);
            em.persist(product20);
            em.persist(product21);
            em.persist(product22);
            em.persist(product23);
            em.persist(product24);
            em.persist(product25);
            em.persist(product26);
            em.persist(product27);

        }

        private Product createProduct(String name, Integer price, String description,
            Integer quantity, MainCategory mainCategory, SubCategory subCategory, List<String> imgURL) {
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setDescription(description);
            product.setQuantity(quantity);
            product.setMainCategory(mainCategory);
            product.setSubCategory(subCategory);
            product.setImageUrls(imgURL);
            return product;
        }
    }
}

