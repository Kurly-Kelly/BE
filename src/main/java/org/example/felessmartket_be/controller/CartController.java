package org.example.felessmartket_be.controller;

import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.example.felessmartket_be.domain.Member;
import org.example.felessmartket_be.domain.dto.cartDto.CartItemRequestDto;
import org.example.felessmartket_be.domain.dto.cartDto.CartResponseDto;
import org.example.felessmartket_be.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    // 장바구니에 상품 추가
    @PostMapping
    public ResponseEntity<?> addToCart(
        @RequestBody @Valid CartItemRequestDto cartItemRequestDto,
        Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access the cart.");
        }
        // principal 에서 member 객체 가져옴
        Member member = extractMemberFromPrincipal(principal);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Invalid user details.");
        }

        Long cartItemId;

        try {
            // DTO 값 확인
            System.out.println("cartItemId: " + cartItemRequestDto.getCartItemId());
            System.out.println("quantity: " + cartItemRequestDto.getQuantity());
            System.out.println("Principal name: " + principal.getName());

            cartItemId = cartService.addCart(cartItemRequestDto, member.getUsername());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok(cartItemId);
    }

    // username 을 통해 장바구니에 담긴 상품 목록 조회
    @GetMapping
    @ResponseBody
    public ResponseEntity<CartResponseDto> getCart(Principal principal) {
        Member member = extractMemberFromPrincipal(principal);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(null);
        }
        // username 으로 사용자의 장바구니 정보를 조회하고, 이를 cartResponseDto 에 저장
        CartResponseDto cartResponseDto = cartService.getCart(member.getUsername());
        return ResponseEntity.ok(cartResponseDto);
    }

    private Member extractMemberFromPrincipal(Principal principal) {
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            Object principalObj = ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
            if (principalObj instanceof Member) {
                return (Member) principalObj;
            }
        }
        return null;
    }
}
