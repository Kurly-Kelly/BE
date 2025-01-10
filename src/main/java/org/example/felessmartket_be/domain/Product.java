package org.example.felessmartket_be.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    Long id;
    String name;
    String description;
    Integer price;
    Integer quantity;
    @Enumerated(EnumType.STRING)
    ProductStatus productStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MainCategory mainCategory; // 대분류

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubCategory subCategory; // 소분류

    String imgURL; //추후 MultipartFile 방식으로 전환

}