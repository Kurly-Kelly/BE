package org.example.be.repository;

import java.util.List;
import java.util.Optional;
import org.example.be.domain.LikeItem;
import org.example.be.domain.Member;
import org.example.be.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeItemRepository extends JpaRepository<LikeItem, Long> {

    boolean existsByMemberUsernameAndProductId(String username, Long productId);
    // 상품 ID를 기준으로 상품의 좋아요 수
    long countByProductId(Long productId);

    // 사용자 username 기준으로 LikeItem 목록 조회
    List<LikeItem> findByMemberUsername(String username);

    // username 과 상품 ID로 LIkeItem 조회
    Optional<LikeItem> findByMemberUsernameAndProductId(String username, Long productId);
}
