package org.example.felessmartket_be;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.example.felessmartket_be.domain.Cart;
import org.example.felessmartket_be.domain.Category;
import org.example.felessmartket_be.domain.Member;
import org.example.felessmartket_be.domain.Product;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


//package org.example.felessmartket_be;
//
//
//import jakarta.annotation.PostConstruct;
//import jakarta.persistence.EntityManager;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import lombok.RequiredArgsConstructor;
//import org.example.felessmartket_be.domain.Cart;
//import org.example.felessmartket_be.domain.Category;
//import org.example.felessmartket_be.domain.Member;
//import org.example.felessmartket_be.domain.Product;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//@Component
//@RequiredArgsConstructor
//public class InitDB {
//
//    private final InitService initService;
//
//    @PostConstruct
//    public void init() {
//        initService.productInit();
//    }
//
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class InitService {
//
//        private final EntityManager em;
//
//
//        @RequiredArgsConstructor
//        public enum Genre {
//
//        }
//
//        public void productInit() {
//            LocalDateTime today = LocalDateTime.now();
//            today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm:ss"));
//
//            /*LocalDateTime releaseDate = today.minusDays(6);
//            LocalDate startDate = LocalDate.now().plusDays(1);
//            LocalDate endDate = LocalDate.now().plusDays(10);
//*/
//            for (int i = 1; i < 10; i++) {
//                Member member = userCreate(i + "abc", "1" + i, "1" + i, i + "a@naver.com");
//                Cart cart = cartCreate(member, i, i);
//
//                em.persist(member);
//                em.persist(cart);
//            }
//
//            Category[] categories = Category.values();
//            for (int i = 1; i <= 10; i++) {
//                Product product = productCreate(
//                    "Product " + i,             // 상품 이름
//                    i * 1000,                   // 상품 가격
//                    "Description for product " + i, // 상품 설명
//                    i * 10,                      // 수량
//                    categories[i % categories.length], // 카테고리 순환
//                    "https://example.com/product" + i + ".jpg" // 이미지 URL
//                );
//
//                em.persist(product); // 상품 객체를 영속성 컨텍스트에 추가
//            }
//        }
//
//        public static Member userCreate(String name, String password, String phoneNumber,
//            String email) {
//            return new Member(name, password, phoneNumber, email);
//        }
//
//        public static Cart cartCreate(Member member, Integer quantity, Integer price) {
//            return new Cart(member, quantity, price);
//        }
//
//        public static Product productCreate(String name, Integer price, String description,
//            Integer quantity, Category category, String imgURL) {
//            return new Product(name, price, description, quantity, category, imgURL);
//        }
//    }
//}
