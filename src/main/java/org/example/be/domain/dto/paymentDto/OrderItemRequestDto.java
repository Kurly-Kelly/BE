package org.example.be.domain.dto.paymentDto;


import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class OrderItemRequestDto {

    private String tossOrderId;
    private String paymentKey;
    private Integer balanceAmount;
    private String paymentMethod;
    private Integer totalPrice;
    private ShippingDto shipping;
    private List<OrderItemDto> orderItems;


    @Getter
    @Setter
    public static class ShippingDto {
        private String zipCode;
        private String address;
        private String detailAddress;
        private String deliveryNote;
    }

    @Getter
    @Setter
    public static class OrderItemDto {
        private Long productId;
        private int quantity;
    }
}
