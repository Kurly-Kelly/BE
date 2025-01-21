package org.example.be.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderItem {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderItem_id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Orders order;


    public static OrderItem createOrderItem(Orders orders, Product product, Integer quantity) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(orders);
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);
        return orderItem;
    }

}
