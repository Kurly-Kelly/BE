package org.example.felessmartket_be.domain.dto.paymentDto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDto {

    private String orderId;
    private String paymentKey;
    private Integer balanceAmount;
    private String paymentMethod;

}
