package org.example.be.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.be.domain.LikeItem;
import org.example.be.domain.Member;
import org.example.be.domain.Product;
import org.example.be.repository.LikeItemRepository;
import org.example.be.repository.MemberRepository;
import org.example.be.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class LikeItemService {
    LikeItemRepository likeItemRepository;
    MemberRepository memberRepository;
    ProductRepository productRepository;

    // 상품 ID로 해당 상품의 좋아요 수 조회
    public long getLikeCountForProductId(Long productId) {
        return likeItemRepository.countByProductId(productId);
    }

    // username 을 가진 회원이 좋아요한 상품 목록 조회
    public List<Product> getLikedProductsForUsername(String username) {
        // username 으로 LikeItem 목록 조회
        List<LikeItem> likeItems = likeItemRepository.findByMemberUsername(username);
        //각 LikeItem 에서 Product 추출
        return likeItems.stream()
            .map(LikeItem::getProduct)
            .collect(Collectors.toList());
    }

    // 좋아요 추가: 특정 회원이 특정 상품에 좋아요
    public boolean addLike(String username, Long productId) {
        if(productId == null) {
            throw new IllegalArgumentException(("상품 ID가 null입니다."));
        }
        // 중복 좋아요 방지를 위해 존재 여부 확인
        Optional<LikeItem> existing = likeItemRepository.findByMemberUsernameAndProductId(username, productId);
        if(existing.isPresent()) {
            log.warn("회원 {}는 상품 {}에 대해 이미 찜했습니다..", username, productId);
            return false;
        }

        // 회원과 상품 조회
        Member member = memberRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        // 새로운 LikeItem 생성
        LikeItem likeItem = new LikeItem();
        likeItem.setMember(member);
        likeItem.setProduct(product);
        likeItem.setCreatedAt(LocalDateTime.now());

        // LikeItem 저장
        likeItemRepository.save(likeItem);
        log.info("회원 {}가 상품 {}을 찜했습니다..", username, productId);
        return true;
    }

    // 좋아요 취소: 특정 회원이 특정 상품에 대해 좋아요를 취소
    public boolean cancelLike(String username, Long productId) {
        // username과 productId로 해당 LikeItem 조회
        Optional<LikeItem> likeItemOpt = likeItemRepository.findByMemberUsernameAndProductId(username, productId);

        if(likeItemOpt.isPresent()) {
            likeItemRepository.delete(likeItemOpt.get());
            log.info("회원 {}의 상품 {} 찜을 취소했습니다", username, productId);
            return true;
        } else {
            log.warn("회원 {}가 상품 {}에 대한 찜 기록을 찾을 수 없습니다.", username, productId);
            return false;
        }
    }
}
