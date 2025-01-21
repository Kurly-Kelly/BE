package org.example.be.repository;


import org.example.be.domain.Orders;
import org.example.be.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findByOrder(Orders orders);
}
