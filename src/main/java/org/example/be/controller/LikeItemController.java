package org.example.be.controller;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.be.domain.Product;
import org.example.be.domain.dto.LikeDto.LikeRequestDto;
import org.example.be.domain.dto.LikeDto.LikeResponseDto;
import org.example.be.domain.dto.productDto.ProductResponseDto;
import org.example.be.repository.LikeItemRepository;
import org.example.be.service.LikeItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/like")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class LikeItemController {
    LikeItemService likeItemService;
    // 좋아요 추가
    // POST /like
    @PostMapping
    public ResponseEntity<LikeResponseDto> addLike(@RequestBody LikeRequestDto request) {
        boolean success = likeItemService.addLike(request.getUsername(), request.getProductId());
        if (success) {
            long likeCount = likeItemService.getLikeCountForProductId(request.getProductId());
            return ResponseEntity.ok(
                new LikeResponseDto(true, "상품이 찜 목록에 추가되었습니다.", likeCount)
            );
        } else {
            return ResponseEntity.badRequest().body(
                new LikeResponseDto(false, "이미 찜한 상품입니다.", null)
            );
        }
    }

    @DeleteMapping
    public ResponseEntity<LikeResponseDto> cancelLike(@RequestBody LikeRequestDto request) {
        boolean success = likeItemService.cancelLike(request.getUsername(), request.getProductId());
        if (success) {
            long likeCount = likeItemService.getLikeCountForProductId(request.getProductId());
            return ResponseEntity.ok(
                new LikeResponseDto(true, "찜한 상품이 취소되었습니다.", likeCount)
            );
        } else {
            return ResponseEntity.badRequest().body(
                new LikeResponseDto(false, "찜 기록이 없습니다.", null)
            );
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Long> getLikeCount(@PathVariable Long productId) {
        long likeCount = likeItemService.getLikeCountForProductId(productId);
        return ResponseEntity.ok(likeCount);
    }

    @GetMapping("/member/{username}")
    public ResponseEntity<List<ProductResponseDto>> getLikedProducts(@PathVariable String username) {
        List<Product> products = likeItemService.getLikedProductsForUsername(username);
        List<ProductResponseDto> productDtos = products.stream()
            .map(ProductResponseDto::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> isProductLiked(@RequestParam String username, @RequestParam Long productId) {
        boolean liked = likeItemService.existsByMemberUsernameAndProductId(username, productId);
        return ResponseEntity.ok(liked);
    }
}
