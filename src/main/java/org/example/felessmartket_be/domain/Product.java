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

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    Category category;
    String imgURL; //추후 MultipartFile 방식으로 전환

//    public Product(String name, Integer price, String description, Integer quantity, String category, String imgURL){
//        this.name = name;
//        this.price = price;
//        this.description = description;
//        this.quantity = quantity;
//        this.category = category;
//        this.imgURL = imgURL;
//    }
}