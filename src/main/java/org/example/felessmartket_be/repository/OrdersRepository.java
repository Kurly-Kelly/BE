package org.example.felessmartket_be.repository;


import org.example.felessmartket_be.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Orders findByTossOrderId(String tossOrderId);
}
