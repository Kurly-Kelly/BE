package org.example.be.service;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.be.domain.Member;
import org.example.be.domain.Orders;
import org.example.be.domain.Payment;
import org.example.be.domain.PaymentStatus;
import org.example.be.domain.Product;
import org.example.be.domain.Shipping;
import org.example.be.domain.dto.paymentDto.OrderItemRequestDto;
import org.example.be.domain.dto.paymentDto.OrderItemRequestDto.OrderItemDto;
import org.example.be.domain.dto.paymentDto.OrderItemRequestDto.ShippingDto;
import org.example.be.domain.dto.paymentDto.OrdersRequestDto;
import org.example.be.repository.OrdersRepository;
import org.example.be.repository.PaymentRepository;
import org.example.be.repository.ShippingRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final RedisService redisService;
    private final long authCodeExpirationMillis = 300000;
    private final String widgetSecretKey = "test_gsk_docs_OaPz8L5KdmQXkzRz3y47BMw6";
    private final OrdersService ordersService;
    private final PaymentRepository paymentRepository;
    private final ShippingRepository shippingRepository;
    private final OrdersRepository ordersRepository;

    public Boolean tempSave(OrdersRequestDto request) {
        try{
            return redisService.setTossValuesIfAbsent(
                request.getOrderId(),
                request.getAmount(),
                Duration.ofMillis(this.authCodeExpirationMillis)
            );
        } catch (Exception e) {
            throw new RuntimeException("toss - orderId와 amount 저장 중 오류", e);
        }
    }

    public Boolean checkVerifyAmount(OrdersRequestDto request) {
        Integer amount = redisService.getTossValuesAsInteger(request.getOrderId());
        Integer requestedAmount = request.getAmount();
        return requestedAmount.equals(amount);
    }

    public JSONObject confirmPayment(String paymentKey, String orderId, String amount, Member member) {
        JSONParser parser = new JSONParser();
        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);
        obj.put("paymentKey", paymentKey);

        // Basic Authentication 헤더 생성
        String encodedAuth = "Basic " + Base64.getEncoder()
            .encodeToString((widgetSecretKey + ":")
            .getBytes(StandardCharsets.UTF_8));

        HttpURLConnection connection = null;
        JSONObject jsonObject;


        try {
            // 토스페이먼츠 결제 승인 API URL
            URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization", encodedAuth);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // JSON 데이터 전송
            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(obj.toString().getBytes(StandardCharsets.UTF_8));
            }

            int code = connection.getResponseCode();
            boolean isSuccess = code == 200;

            InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

            // 응답 데이터 파싱
            Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
            jsonObject = (JSONObject) parser.parse(reader);
            responseStream.close();

            log.info("isSuccess: {}", isSuccess);

            if (isSuccess) {
                Orders newOrders = ordersService.createOrders(member, orderId);
                createPaymentByOrder(newOrders);
                return jsonObject;
            } else {
                log.error("Payment confirmation failed: {}", jsonObject);
            }

            return jsonObject;
        } catch (Exception e) {
            log.error("Error during payment confirmation", e);
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", "Internal Server Error");
            return errorResponse;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void createPaymentByOrder(Orders orders) {
        Payment newPayment = Payment.createPayment(orders);
        log.info("저장된 Payment: {}", paymentRepository.save(newPayment));
    }

    public void savePayment(OrderItemRequestDto request) {
        Orders orders = ordersService.findByTossOrderId(request.getTossOrderId());
        mappingPaymentInfo(ordersService.saveOrderItems(request, orders), request);
        mappingShippingInfo(request, orders);
    }


    public void mappingPaymentInfo(Orders orders, OrderItemRequestDto request) {
        Payment payment = paymentRepository.findByOrder(orders);
        payment.setTotalAmount(request.getTotalPrice());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setTossOrderId(request.getTossOrderId());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        payment.setUsedPoint(request.getUsedPoint());
        paymentRepository.save(payment);
    }

    private void mappingShippingInfo(OrderItemRequestDto request, Orders orders) {
        Shipping shipping = Shipping.createShipping(orders);
        ShippingDto deliveryInfo = request.getShipping();
        shipping.setPostalCode(deliveryInfo.getZipCode());
        shipping.setAddress(deliveryInfo.getAddress());
        shipping.setDetailAddress(deliveryInfo.getDetailAddress());
        shipping.setCountry("Korea");
        shipping.setDeliveryNote(deliveryInfo.getDeliveryNote());
        orders.setShipping(shipping);
        shippingRepository.save(shipping);
        ordersRepository.save(orders);
    }




}
