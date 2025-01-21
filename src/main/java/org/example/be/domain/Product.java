package org.example.be.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.List;
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
    Integer discount;

    @Enumerated(EnumType.STRING)
    DiscountStatus discountStatus;

    @Enumerated(EnumType.STRING)
    ProductStatus productStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MainCategory mainCategory; // 대분류

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubCategory subCategory; // 소분류

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url", length = 1000) // 길이를 1000으로 설정
    private List<String> imageUrls = new ArrayList<>();
}