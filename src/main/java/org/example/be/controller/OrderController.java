package org.example.be.controller;


import java.security.Principal;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.be.domain.Member;
import org.example.be.domain.OrderItem;
import org.example.be.domain.Orders;
import org.example.be.domain.dto.paymentDto.OrderItemRequestDto;
import org.example.be.domain.dto.paymentDto.OrdersResponseDto;
import org.example.be.service.OrdersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrdersService ordersService;

    @PostMapping("")
    public ResponseEntity<?> saveOrders(@RequestBody OrderItemRequestDto request, Principal principal) {
        Member member = extractMemberFromPrincipal(principal);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(null);
        }
        Orders newOrders = ordersService.createOrders(member, "empty");
        ordersService.saveOrderItems(request, newOrders);
        return ResponseEntity.ok("");
    }



    private Member extractMemberFromPrincipal(Principal principal) {
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            Object principalObj = ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
            if (principalObj instanceof Member) {
                return (Member) principalObj;
            }
        }
        return null;
    }

}
