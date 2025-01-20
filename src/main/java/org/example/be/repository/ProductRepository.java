package org.example.be.repository;

import java.util.List;
import java.util.Optional;
import org.example.be.domain.MainCategory;
import org.example.be.domain.Product;
import org.example.be.domain.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    // 상품 이름에 keyword 를 포함한 상품 검색- 대소문자 구분 안함
    List<Product> findByNameContainingIgnoreCase(String keyword);
    List<Product> findBySubCategory(SubCategory subCategory);
    List<Product> findByMainCategory(MainCategory mainCategory);
    @Query("SELECT p FROM Product p JOIN FETCH p.imageUrls WHERE p.id = :id")
    Optional<Product> findByIdWithImages(@Param("id") Long id);
    List<Product> findTop10ByOrderByPriceDesc();
    Page<Product> findByMainCategory(MainCategory mainCategory, Pageable pageable);
    Page<Product> findBySubCategory(SubCategory subCategory, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);


}