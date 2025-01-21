package org.example.be.controller;

import java.security.Principal;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.be.domain.Member;
import org.example.be.domain.Orders;
import org.example.be.domain.dto.paymentDto.OrderItemRequestDto;
import org.example.be.domain.dto.paymentDto.OrdersRequestDto;
import org.example.be.domain.dto.paymentDto.OrdersResponseDto;
import org.example.felessmartket_be.domain.dto.paymentDto.PaymentRequestDto;
import org.example.be.service.OrdersService;
import org.example.be.service.PaymentService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> OrderSave(@RequestBody OrdersRequestDto request) {
        Boolean result = paymentService.tempSave(request);
        if (result) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Order saved successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("success", false, "message", "OrderId already exists"));
        }
    }

    @PostMapping("/verifyAmount")
    public ResponseEntity<Boolean> verifyAmount(@RequestBody OrdersRequestDto request) {
        Boolean result = paymentService.checkVerifyAmount(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/confirm")
    public ResponseEntity<JSONObject> confirmPayment(@RequestBody String jsonBody, Principal principal) throws Exception {
        JSONParser parser = new JSONParser();
        String orderId;
        String amount;
        String paymentKey;
        log.info("request JsonBody: {}", jsonBody);
        try {
            // 클라이언트에서 받은 JSON 요청 바디
            JSONObject requestData = (JSONObject) parser.parse(jsonBody);
            paymentKey = (String) requestData.get("paymentKey");
            orderId = (String) requestData.get("orderId");
            amount = (String) requestData.get("amount");

            log.info("orderId: {}", orderId);
            log.info("amount: {}", amount);
            log.info("paymentKey: {}", paymentKey);

        } catch (ParseException e) {
            log.error("JSON 파싱 오류: {}", jsonBody, e);
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", "Invalid JSON format");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        Member member = extractMemberFromPrincipal(principal);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(null);
        }

        // 결제 승인 로직 실행
        JSONObject response = paymentService.confirmPayment(paymentKey, orderId, amount, member);
        log.info("결제 승인 로직 결과: {}", response);
        // 결제 승인 결과에 따라 HTTP 상태 코드 설정
        if (response.containsKey("error")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/complete")
    public ResponseEntity<?> completePayment(@RequestBody OrderItemRequestDto requestDto, Principal principal) {
        Member member = extractMemberFromPrincipal(principal);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        paymentService.savePayment(member, requestDto);
        return ResponseEntity.ok(Map.of("result", "success"));
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
