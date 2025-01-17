package org.example.felessmartket_be;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            LocalDateTime today = LocalDateTime.now();
            log.info("Initialization started at: {}", today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            for (int i = 1; i <= 5; i++) {
                String email = i + "a@naver.com";

                // 이메일 중복 확인
                if (emailExists(email)) {
                    log.info("Skipping duplicate email: {}", email);
                    continue;
                }

                // 회원 생성 및 저장
                Member member = createUser(i + "abc", "abc" + i, "password" + i, "010-0000-000" + i, email);
                em.persist(member);

                // 장바구니 생성 및 저장
                Cart cart = Cart.createCart(member);
                em.persist(cart);

                // 카테고리별 상품 초기화
                initializeProducts(cart, i);
            }

            log.info("Initialization completed.");
        }

        // 상품 초기화
        private void initializeProducts(Cart cart, int index) {
            int productIndex = 1;

            // MainCategory 별로 상품 생성
            for (MainCategory mainCategory : MainCategory.values()) {
                List<SubCategory> subCategories = SubCategory.getSubCategoriesByMain(mainCategory);

                for (SubCategory subCategory : subCategories) {
                    Product product = createProduct(
                        subCategory.getDisplayName() + " Product " + index + "-" + productIndex,
                        productIndex * 1000,
                        "Description for " + subCategory.getDisplayName() + " product " + index + "-" + productIndex,
                        productIndex * 10,
                        mainCategory,
                        subCategory,
                        "https://example.com/" + subCategory.name().toLowerCase() + "_product" + productIndex + ".jpg"
                    );
                    em.persist(product);

                    // 장바구니 아이템 생성
                    CartItem cartItem = CartItem.createCartItem(cart, product, productIndex * 2);
                    em.persist(cartItem);

                    productIndex++;
                }
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

        private Product createProduct(String name, Integer price, String description,
            Integer quantity, MainCategory mainCategory, SubCategory subCategory, String imgURL) {
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setDescription(description);
            product.setQuantity(quantity);
            product.setMainCategory(mainCategory);
            product.setSubCategory(subCategory);
            product.setImgURL(imgURL);
            return product;
        }
    }
}
