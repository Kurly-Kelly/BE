package org.example.felessmartket_be.domain.dto.cartDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 장바구니 조회
@Getter
@AllArgsConstructor
public class CartItemDto {
    private Long cartItemId;
    private Long productId;
    private String productName;
    private int quantity;
    private int price;
    private String imgURL;
}

