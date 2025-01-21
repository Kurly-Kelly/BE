package org.example.be.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.be.domain.Member;
import org.example.be.domain.OrderItem;
import org.example.be.domain.Orders;
import org.example.be.domain.Product;
import org.example.be.domain.dto.paymentDto.OrderItemRequestDto;
import org.example.be.domain.dto.paymentDto.OrderItemRequestDto.OrderItemDto;
import org.example.be.repository.OrderItemRepository;
import org.example.be.repository.OrdersRepository;
import org.example.be.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final RedisService redisService;

    public Orders createOrders(Member member, String tossOrderId) {
        Orders newOrders = Orders.createOrders(member);
        newOrders.setTossOrderId(tossOrderId);
        return ordersRepository.save(newOrders);
    }

    public Orders findByTossOrderId(String TossOrderId) {
        return ordersRepository.findByTossOrderId(TossOrderId);
    }

    public Orders saveOrderItems(OrderItemRequestDto request, Orders orders) {
        Map<Product, Integer> products = transProduct(request);
        for (Product product : products.keySet()) {
            OrderItem newOrderItem = OrderItem.createOrderItem(orders, product, products.get(product));
            orderItemRepository.save(newOrderItem);
        }
        orders.updateTotalPrice(request.getTotalPrice());
        return ordersRepository.save(orders);
    }

    public Map<Product, Integer> transProduct(OrderItemRequestDto request) {
        List<OrderItemDto> orderItemDtos = request.getOrderItems();
        Map<Product, Integer> products = new HashMap<>();

        for (OrderItemDto orderItemDto : orderItemDtos) {
            Product product = productRepository.findById(orderItemDto.getProductId()).orElse(null);
            Integer quantity = orderItemDto.getQuantity();
            products.put(product, quantity);
        }
        return products;
    }





}
