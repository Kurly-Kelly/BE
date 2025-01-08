package org.example.felessmartket_be;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.felessmartket_be.domain.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
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
            today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // 회원 및 장바구니 초기화
            for (int i = 1; i <= 5; i++) {
                String email = i + "a@naver.com";

                // 이메일 중복 확인
                boolean exists = emailExists(email);
                if (exists) {
                    System.out.println("Skipping duplicate email: " + email);
                    continue;
                }
                // 회원 생성 및 저장
                Member member = userCreate(i + "abc", "password" + i, "010-0000-000" + i, email);
                em.persist(member);

                // 장바구니 생성 및 저장
                Cart cart = Cart.createCart(member);
                em.persist(cart);

                // 상품 생성 및 저장
                initializeProducts(cart, i);
//                Category[] categories = Category.values();
//                for (int j = 1; j <= 3; j++) {
//                    Product product = productCreate(
//                        "Product " + i + "-" + j,    // 상품 이름
//                        j * 1000,                    // 상품 가격
//                        "Description for product " + i + "-" + j, // 상품 설명
//                        j * 10,                      // 수량
//
//                        categories[j % categories.length],
//                        "https://example.com/product" + j + ".jpg"
//                    );
//                    em.persist(product);
//
//                    // 장바구니 아이템 생성 및 저장
//                    CartItem cartItem = CartItem.createCartItem(cart, product, j * 2);
//                    em.persist(cartItem);
                }
            }

            // 상품 초기화
            private void initializeProducts(Cart cart, int index) {
                List<Category> meatCategories = Category.getChildCategories(Category.MEAT);
                List<Category> vegetableCategories = Category.getChildCategories(Category.VEGETABLE);

                int productIndex = 1;

                // MEAT 카테고리 상품 생성
                for (Category category : meatCategories) {
                    Product product = productCreate(
                        "Meat Product " + index + "-" + productIndex,
                        productIndex * 1000,
                        "Description for meat product " + index + "-" + productIndex,
                        productIndex * 10,
                        category,
                        "https://example.com/meat_product" + productIndex + ".jpg"
                    );
                    em.persist(product);

                    // 장바구니 아이템 생성
                    CartItem cartItem = CartItem.createCartItem(cart, product, productIndex * 2);
                    em.persist(cartItem);

                    productIndex++;
                }

                // VEGETABLE 카테고리 상품 생성
                for (Category category : vegetableCategories) {
                    Product product = productCreate(
                        "Vegetable Product " + index + "-" + productIndex,
                        productIndex * 1000,
                        "Description for vegetable product " + index + "-" + productIndex,
                        productIndex * 10,
                        category,
                        "https://example.com/vegetable_product" + productIndex + ".jpg"
                    );
                    em.persist(product);

                    // 장바구니 아이템 생성
                    CartItem cartItem = CartItem.createCartItem(cart, product, productIndex * 2);
                    em.persist(cartItem);

                    productIndex++;
                }

            }
        /**
         *  이메일 중복 확인
         */
        private boolean emailExists(String email) {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(m) FROM Member m WHERE m.email = :email", Long.class);
            query.setParameter("email", email);
            return query.getSingleResult() > 0;
        }

        /**
         * 회원 생성
         */
        public static Member userCreate(String name, String password, String phoneNumber, String email) {
            return new Member(name, password, phoneNumber, email);
        }

        /**
         * 상품 생성
         */
        public static Product productCreate(String name, Integer price, String description,
            Integer quantity, Category category, String imgURL) {
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setDescription(description);
            product.setQuantity(quantity);
            product.setCategory(category);
            product.setImgURL(imgURL);
            return product;
        }
    }
}
