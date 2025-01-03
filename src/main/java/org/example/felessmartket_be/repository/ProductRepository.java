package org.example.felessmartket_be.repository;

import java.util.List;
import org.example.felessmartket_be.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // 상품 이름에 keyword 를 포함한 상품 검색- 대소문자 구분 안함
    List<Product> findByNameContainingIgnoreCase(String keyword);
    List<Product> findByCategoryIgnoreCase(String category);



}