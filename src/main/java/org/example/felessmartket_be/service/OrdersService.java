package org.example.felessmartket_be.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.felessmartket_be.domain.Member;
import org.example.felessmartket_be.domain.OrderItem;
import org.example.felessmartket_be.domain.Orders;
import org.example.felessmartket_be.domain.Product;
import org.example.felessmartket_be.domain.dto.orderDto.OrderItemRequestDto;
import org.example.felessmartket_be.domain.dto.orderDto.OrderItemRequestDto.OrderItemDto;
import org.example.felessmartket_be.repository.OrderItemRepository;
import org.example.felessmartket_be.repository.OrdersRepository;
import org.example.felessmartket_be.repository.ProductRepository;
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

    public void saveOrderItems(OrderItemRequestDto request, Orders orders) {
        Map<Product, Integer> products = transProduct(request);
        for (Product product : products.keySet()) {
            OrderItem newOrderItem = OrderItem.createOrderItem(orders, product, products.get(product));
            orderItemRepository.save(newOrderItem);
        }
        orders.updateTotalPrice(request.getTotalPrice());
        ordersRepository.save(orders);
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
