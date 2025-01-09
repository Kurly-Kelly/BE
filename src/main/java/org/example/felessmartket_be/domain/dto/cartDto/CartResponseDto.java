package org.example.felessmartket_be.domain.dto.cartDto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartResponseDto {
    private Long cartId;
    private List<CartItemDto> cartItems;
    private Long totalPrice;
}
