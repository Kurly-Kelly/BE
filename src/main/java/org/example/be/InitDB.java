package org.example.be;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.be.domain.*;
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
            LocalDateTime today = LocalDateTime.now();
            log.info("Initialization started at: {}", today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            for (int i = 1; i <= 5; i++) {
                String email = i + "a@naver.com";

                // 이메일 중복 확인
                boolean exists = emailExists(email);
                if (exists) {
                    System.out.println("Skipping duplicate email: " + email);
                    continue;
                }

                // 회원 생성 및 저장
                Member member = createUser(i + "abc","abc" + i , "password" + i, "010-0000-000" + i, email);
                em.persist(member);

                // 장바구니 생성 및 저장
                Cart cart = Cart.createCart(member);
                em.persist(cart);

                // 상품 생성 부분 호출
                initializeProducts(cart, i);
            }

            log.info("Initialization completed.");
        }
        // 상품 생성
        private void initializeProducts(Cart cart, int index) {

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

            Product product1 = createProduct("해남 꿀고구마 베니하루카",27200,"해남 꿀고구마 베니하루카 2/3/5/10kg",Delivery.GENERAL_DELIVERY,45,MainCategory.VEGETABLE,SubCategory.ROOT_VEGETABLE,imgUrl1);
            Product product2 = createProduct("포슬포슬 햇 수미감자",24900,"포슬포슬 햇 수미감자 3kg/5kg/10kg(국내산)",Delivery.EARLY_DELIVERY,45,MainCategory.VEGETABLE,SubCategory.ROOT_VEGETABLE,imgUrl2);
            Product product3 = createProduct("제주 구좌 당근 3kg",26900,"제주 구좌 당근 2/3/5kg (국내산)",Delivery.SELLER_DELIVERY,45,MainCategory.VEGETABLE,SubCategory.ROOT_VEGETABLE,imgUrl3);
            Product product4 = createProduct("매일야채 고농축 당근의힘",40900,"매일야채 고농축 당근의힘 125ml*24팩",Delivery.GENERAL_DELIVERY,45,MainCategory.VEGETABLE,SubCategory.ROOT_VEGETABLE,imgUrl4);
            Product product5 = createProduct("당근 라페 프랑스식 샐러드",13000,"당근 라페 프랑스식 샐러드 200g",Delivery.GENERAL_DELIVERY,45,MainCategory.VEGETABLE,SubCategory.ROOT_VEGETABLE,imgUrl5);
            Product product6 = createProduct("산지직송 제주 구좌 햇 당근 흙당근 10kg",30000,"산지직송 제주 구좌 햇 당근 흙당근 국산 국내산 제철 5kg 10kg 주스용 요리용 업소용",Delivery.EARLY_DELIVERY,45,MainCategory.VEGETABLE,SubCategory.ROOT_VEGETABLE,imgUrl6);
            Product product7 = createProduct("반건조 스위트 고구마 70g",29900,"국민 영양간식 고구마 농장",Delivery.SELLER_DELIVERY,45,MainCategory.VEGETABLE,SubCategory.ROOT_VEGETABLE,imgUrl7);
            Product product8 = createProduct("황성주박사의 국산콩 두유 검은콩 고구마",9000,"황성주박사의 국산콩 두유 검은콩 고구마 190ml 48개",Delivery.GENERAL_DELIVERY,45,MainCategory.VEGETABLE,SubCategory.ROOT_VEGETABLE,imgUrl8);
            Product product9 = createProduct("인기간식 감자스낵 과자세트",23900,"인기있는 감자 스낵으로 알차게 구성",Delivery.EARLY_DELIVERY,45,MainCategory.VEGETABLE,SubCategory.ROOT_VEGETABLE,imgUrl9);
            Product product10 = createProduct("해풍월동 시금치 씨앗 채소 텃밭 가꾸기",16500,"뿌리가 붉은 토종 월동 시금치",Delivery.GENERAL_DELIVERY,45,MainCategory.VEGETABLE,SubCategory.LEAF_VEGETABLE,imgUrl10);
            Product product11 = createProduct("시금치 페투치네 파스타 페투치니 면",7500,"시금치 페투치네 파스타 페투치니 면",Delivery.EARLY_DELIVERY,45,MainCategory.VEGETABLE,SubCategory.LEAF_VEGETABLE,imgUrl11);
            Product product12 = createProduct("국내산 싱싱한 시금치",9000,"국내산 싱싱한 시금치 1kg 외",Delivery.SELLER_DELIVERY,45,MainCategory.VEGETABLE,SubCategory.LEAF_VEGETABLE,imgUrl12);
            Product product13 = createProduct("무농약 모듬 쌈채소 샐러드",19900,"무농약 모듬 쌈채소 샐러드 9종내외 500g 1kg(당일수확)",Delivery.GENERAL_DELIVERY,45,MainCategory.VEGETABLE,SubCategory.LEAF_VEGETABLE,imgUrl13);
            Product product14 = createProduct("콩나물 시루 재배기",13900,"[5%쿠폰]콩나물 시루 재배기_2sizes",Delivery.SELLER_DELIVERY,45,MainCategory.VEGETABLE,SubCategory.LEAF_VEGETABLE,imgUrl14);
            Product product15 = createProduct(" 올스텐 만능채칼 양배추",4900,"올스텐 만능채칼 양배추 오이 감자 당근 우엉 쏨땀 필러",Delivery.EARLY_DELIVERY,45,MainCategory.VEGETABLE,SubCategory.GREEN_VEGETABLE,imgUrl15);
            Product product16 = createProduct(" 국내산 간편한 미니오이",13900,"국내산 간편한 미니오이 1kg 외 ",Delivery.GENERAL_DELIVERY,45,MainCategory.VEGETABLE,SubCategory.GREEN_VEGETABLE,imgUrl16);
            Product product17 = createProduct("달콤한 영암 신품종 호박고구마",13900,"달콤한 영암 신품종 호박고구마 호풍미 고구마 세척 3kg 5kg 당근고구마",Delivery.EARLY_DELIVERY,15900,MainCategory.VEGETABLE,SubCategory.ROOT_VEGETABLE,imgUrl17);
            Product product18 = createProduct("해남 꿀밤 고구마 베니하루카",13900,"해남 꿀밤 고구마 베니하루카 [세척] 3kg 5kg 10kg 20kg 황금호박 고구마",Delivery.SELLER_DELIVERY,5700,MainCategory.VEGETABLE,SubCategory.ROOT_VEGETABLE,imgUrl18);
            Product product19 = createProduct("고추참치",36100,"고추참치 250g*10개",Delivery.GENERAL_DELIVERY,45,MainCategory.VEGETABLE,SubCategory.GREEN_VEGETABLE,imgUrl19);
            Product product20 = createProduct("금빛 태양의맛 고추가루 매운맛 베트남산",22900,"금빛 태양의맛 고추가루 매운맛 베트남산 1kg ",Delivery.EARLY_DELIVERY,45,MainCategory.VEGETABLE,SubCategory.GREEN_VEGETABLE,imgUrl20);
            Product product21 = createProduct("경북 부사 꿀사과 못난이",10900,"[경북 부사 꿀사과 못난이 2/3/5kg 택1",Delivery.SELLER_DELIVERY,45,MainCategory.VEGETABLE,SubCategory.LEAF_VEGETABLE,imgUrl21);
            Product product22 = createProduct("콩나물 시루 재배기",13900,"[5%쿠폰]콩나물 시루 재배기_2sizes",Delivery.GENERAL_DELIVERY,45,MainCategory.VEGETABLE,SubCategory.LEAF_VEGETABLE,imgUrl22);
            Product product23 = createProduct("국산 도라지배즙 배즙 1박스",17900,"국산 도라지배즙 배즙 1박스 30포 ",Delivery.EARLY_DELIVERY,45,MainCategory.FRUIT,SubCategory.APPLE_PEAR,imgUrl23);
            Product product24 = createProduct("짱구는 못말려 배도라지스틱",14900,"짱구는 못말려 배도라지스틱 20포  ",Delivery.SELLER_DELIVERY,45,MainCategory.FRUIT,SubCategory.APPLE_PEAR,imgUrl24);
            Product product25 = createProduct(" 제주도 산지직송 새콤달콤 노지감귤",11900,"제주도 산지직송 새콤달콤 노지감귤 4.5kg 9kg",Delivery.GENERAL_DELIVERY,45,MainCategory.FRUIT,SubCategory.CITRUS,imgUrl25);
            Product product26 = createProduct(" 산지직송 무농약 친환경 제주 감귤",95900," 산지직송 무농약 친환경 제주 감귤 로얄과 10kg ",Delivery.EARLY_DELIVERY,45,MainCategory.FRUIT,SubCategory.CITRUS,imgUrl26);
            Product product27 = createProduct("  제주 생선 3종", 36900," 제주 생선 3종, 법성포 굴비 골라담기",Delivery.SELLER_DELIVERY,45,MainCategory.SEAFOOD,SubCategory.FISH,imgUrl27);

            List<Product> products = List.of(
                product1, product2, product3, product4, product5, product6, product7,
                product8, product9, product10, product11, product12, product13,
                product14, product15, product16, product17, product18, product19,
                product20, product21, product22, product23, product24, product25,
                product26, product27
            );

            // 각 상품을 저장하고, 저장 직후 장바구니 아이템 생성
            for (int j = 0; j < products.size(); j++) {
                Product product = products.get(j);
                em.persist(product);

                // 장바구니 아이템 생성
                CartItem cartItem = CartItem.createCartItem(cart, product, 3);
                em.persist(cartItem);
            }


        }
        private boolean emailExists(String email) {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(m) FROM Member m WHERE m.email = :email", Long.class
            );
            query.setParameter("email", email);
            return query.getSingleResult() > 0;
        }

        private Member createUser(String userName, String name, String password, String phoneNumber, String email) {
            return new Member(userName, name, password, phoneNumber, email);
        }

        private Product createProduct(String name, Integer price, String description, Delivery delivery,
            Integer quantity, MainCategory mainCategory, SubCategory subCategory, List<String> imgURL) {
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setDescription(description);
            product.setDelivery(delivery);
            product.setQuantity(quantity);
            product.setMainCategory(mainCategory);
            product.setSubCategory(subCategory);
            product.setImageUrls(imgURL);
            return product;
        }
    }
}
