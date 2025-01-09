package org.example.felessmartket_be.domain.dto.paymentDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.felessmartket_be.domain.Orders;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersResponseDto {

    private String orderId;
    private Integer amount;
    private Boolean result;

//    public static OrdersResponseDto from(Orders orders) {
//        return new OrdersResponseDto(
//            orders.getTossOrderId(),
//            orders.getTotalPrice()
//        );
//    }
}
