package org.example.be.repository;

import org.example.be.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndProductId(Long cartId, Long productId);

}
