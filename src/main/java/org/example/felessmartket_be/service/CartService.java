package org.example.felessmartket_be.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.felessmartket_be.domain.Cart;
import org.example.felessmartket_be.domain.CartItem;
import org.example.felessmartket_be.domain.Member;
import org.example.felessmartket_be.domain.Product;
import org.example.felessmartket_be.domain.dto.cartDto.CartItemDto;
import org.example.felessmartket_be.domain.dto.cartDto.CartItemRequestDto;
import org.example.felessmartket_be.domain.dto.cartDto.CartResponseDto;
import org.example.felessmartket_be.repository.CartItemRepository;
import org.example.felessmartket_be.repository.CartRepository;
import org.example.felessmartket_be.repository.MemberRepository;
import org.example.felessmartket_be.repository.ProductRepository;
import org.springframework.stereotype.Service;

/**
 * 장바구니에 담기(addCart: cartItem 의 Id 반환)
 * 1. 장바구니에 담을 상품 엔티티 조회
 * 2. 회원 엔티티 조회
 * 3. 장바구니 엔티티 조회
 * 4. 회원에게 cart 존재 유무 확인
 * 4-1. 카트 존재 o -> 패스
 * 4-2. 카트 존재 x -> createCart
 * 5. cart 에 cartItem 담기
 * 5-1. 동일 cartItem 이 존재 o -> 기존 수량에서 추가 수량만큼 더하기
 * 5-2. 동일 cartItem 이 존재 x -> cartItem 추가
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    public Long addCart(CartItemRequestDto cartItemRequestDto, String username) {

        // 상품 조회: cartItem 이 된 상품을 productRepository 에서 상픔 Id 조회. 존재하지 않으면 에러처리
        Product product = productRepository.findById(cartItemRequestDto.getCartItemId())
            .orElseThrow(EntityNotFoundException::new);

        // 회원 조회: memberRepository 에서 username 으로 회원 정보 조회. 존재하지 않으면 에러 처리
        Member member = memberRepository.findByUsername(username)
            .orElseThrow(EntityNotFoundException::new);

        // username 으로 장바구니 조회
        Cart cart = cartRepository.findByMember_Username(member.getUsername());
        // 회원에게 장바구니 없으면 장바구니 생성
        if(cart == null) {
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        // 카드에 카트아이템 담기
        // 장바구니에 동일한 상품이 존재하는지 확인
        CartItem savedCartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(),
            product.getId());

        // 동일 상품이 존재하면 수량 추가
        // 동일 상품이 존재하지 않으면 상품 추가
        if(savedCartItem != null) {
            savedCartItem.addQuantity(cartItemRequestDto.getQuantity());
            return savedCartItem.getId();
        } else {
            CartItem cartItem = CartItem.createCartItem(cart, product,
                cartItemRequestDto.getQuantity());
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }
    }

    // 장바구니 조회
    public CartResponseDto getCart(String username) {
        // 회원 조회
        Member member = memberRepository.findByUsername(username)
            .orElseThrow(EntityNotFoundException::new);

        // 장바구니 조회
        Cart cart = cartRepository.findByMember_Username(member.getUsername());
        // 장바구니가 없으면 빈 장바구니 생성 및 반환
        if (cart == null) {
            return new CartResponseDto(null, List.of(), 0L);
        }

        // 장바구니 아이템 DTO로 변환
        List<CartItemDto> cartItems = cart.getCartItem().stream()
            .map(cartItem -> new CartItemDto(
                cartItem.getId(),
                cartItem.getProduct().getId(),
                cartItem.getProduct().getName(),
                cartItem.getQuantity(),
                cartItem.getProduct().getPrice(),
                cartItem.getProduct().getImageUrls().toString()
            ))
            .collect(Collectors.toList());

        // 장바구니 상품 총 금액
        Long totalPrice = cartItems.stream()
            .mapToLong(item -> item.getPrice() * item.getQuantity())
            .sum();

        // 총 가격 포함해 반환
        return new CartResponseDto(cart.getId(), cartItems, totalPrice);
    }

    // 장바구니 수량 업데이트( + , -)
    public void updateCartItemQuantity(Long cartItemId, int quantity, String username) {
        if (quantity < 1) {
            throw new IllegalArgumentException("수량은 1 이상이어야 합니다.");
        }

        Member member = memberRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        Cart cart = cartRepository.findByMember_Username(member.getUsername());
        if (cart == null) {
            throw new EntityNotFoundException("장바구니가 존재하지 않습니다.");
        }

        CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new EntityNotFoundException("장바구니 아이템을 찾을 수 없습니다."));

        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new SecurityException("해당 장바구니 아이템에 접근 권한이 없습니다.");
        }

        cartItem.setQuantity(quantity);
        System.out.println("[DEBUG] 수량 업데이트 완료: " + quantity);
    }

    // 장바구니 삭제
    public void deleteCartItem(Long cartItemId, String username) {
        Member member = memberRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        Cart cart = cartRepository.findByMember_Username(member.getUsername());
        if (cart == null) {
            throw new EntityNotFoundException("장바구니가 존재하지 않습니다.");
        }

        CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new EntityNotFoundException("장바구니 아이템을 찾을 수 없습니다."));

        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new SecurityException("해당 장바구니 아이템에 접근 권한이 없습니다.");
        }

        cartItemRepository.delete(cartItem);
        System.out.println("[DEBUG] 장바구니 아이템 삭제 완료: " + cartItemId);
    }

}
