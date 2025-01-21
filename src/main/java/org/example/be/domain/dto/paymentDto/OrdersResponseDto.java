package org.example.be.domain.dto.paymentDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.be.domain.Orders;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersResponseDto {

    private Long orderId;
    private Integer amount;
    private Long memberId;

    public static OrdersResponseDto from(Orders orders) {
        return new OrdersResponseDto(
            orders.getId(),
            orders.getTotalPrice(),
            orders.getMember().getId()
        );
    }
}
