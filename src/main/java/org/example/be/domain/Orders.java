package org.example.be.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Setter
@Getter
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    Long id;

    @Column(name = "toss_order_id")
    String tossOrderId;

    @OneToOne
    @JoinColumn(name = "shipping_id")
    Shipping shipping;

    @OneToOne
    @JoinColumn(name = "payment_id")
    Payment payment;

    @ManyToOne
    @JoinColumn(name = "memeber_id")
    Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderItem> orderItemList;

    Integer totalPrice;

//    @Enumerated(EnumType.STRING)
    String orderStatus;

    public static Orders createOrders(Member member) {
        Orders orders = new Orders();
        orders.setMember(member);
        return orders;
    }
    public void updateTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

}
