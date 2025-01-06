package org.example.felessmartket_be.controller;

import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.example.felessmartket_be.domain.dto.cartDto.CartItemRequestDto;
import org.example.felessmartket_be.domain.dto.cartDto.CartResponseDto;
import org.example.felessmartket_be.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    // 장바구니에 상품 추가
    @PostMapping
    public ResponseEntity<?> addToCart(
        @RequestBody @Valid CartItemRequestDto cartItemRequestDto,
        Principal principal) {

        String username = principal.getName();  // 현재 로그인한 사용자의 이메일 조회
        Long cartItemId;

        try {
            // DTO 값 확인
            System.out.println("cartItemId: " + cartItemRequestDto.getCartItemId());
            System.out.println("quantity: " + cartItemRequestDto.getQuantity());
            System.out.println("Principal name: " + principal.getName());

            cartItemId = cartService.addCart(cartItemRequestDto, username);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok(cartItemId);
    }

    // username 을 통해 장바구니에 담긴 상품 목록 조회
    @GetMapping
    @ResponseBody
    public ResponseEntity<CartResponseDto> getCart(Principal principal) {
        String username = principal.getName();
        // username 으로 사용자의 장바구니 정보를 조회하고, 이를 cartResponseDto 에 저장
        CartResponseDto cartResponseDto = cartService.getCart(username);
        return ResponseEntity.ok(cartResponseDto);
    }


}
