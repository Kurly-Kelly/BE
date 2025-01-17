package org.example.be.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    Integer id;

    @OneToOne(mappedBy = "payment")
    Orders order;

    @Column(name = "toss_order_id")
    String tossOrderId;

    @Column(name = "toss_payment_mehtod")
    String tossPaymentMethod;

    @Column(name = "toss_payment_status")
    String tossPaymentStatus;

    @Column(name = "total_amount")
    Integer totalAmount;

    LocalDateTime paymentDate; //결제 시간

    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod; //결제 방법(카카오 페이, 휴대폰 번호, 간편결제, 신용카드)

    @Enumerated(EnumType.STRING)
    PaymentStatus paymentStatus; //결제 상태(결제 완료, 결제 대기, 결제 실패)

    public static Payment createPayment(Orders orders) {
        Payment payment = new Payment();
        payment.setOrder(orders);
        return payment;
    }

}
