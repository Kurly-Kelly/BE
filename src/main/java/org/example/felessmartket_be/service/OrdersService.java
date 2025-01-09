package org.example.felessmartket_be.service;

import lombok.RequiredArgsConstructor;
import org.example.felessmartket_be.domain.Orders;
import org.example.felessmartket_be.domain.dto.paymentDto.OrdersRequestDto;
import org.example.felessmartket_be.repository.OrdersRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final RedisService redisService;

    public Orders OrdersCreate(OrdersRequestDto request) {
        Orders newOrders = new Orders();
        newOrders.setTossOrderId(request.getOrderId());
        newOrders.setTotalPrice(request.getAmount());
        ordersRepository.save(newOrders);
        return newOrders;
    }

    public Orders findByOrderId(String orderId) {
        return ordersRepository.findByTossOrderId(orderId);
    }





}
