package org.example.be.domain.dto.paymentDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrdersRequestDto {


    private String orderId;
    private Integer amount;

}
