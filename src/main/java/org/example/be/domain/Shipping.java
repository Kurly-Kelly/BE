package org.example.be.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@Entity
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipping_id")
    Integer id;

    @OneToOne(mappedBy = "shipping")
    Orders order;

    String address; //주소
    String detailAddress; //주소
    String postalCode; //우편번호
    String country; //나라
    String contactPhone; //휴대폰 번호
    String deliveryNote; //배송노트
    LocalDateTime createdAt; //날짜

    public static Shipping createShipping(Orders orders) {
        Shipping shipping = new Shipping();
        shipping.setOrder(orders);
        return shipping;
    }

}