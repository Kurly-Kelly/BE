package org.example.felessmartket_be.service;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.felessmartket_be.domain.Orders;
import org.example.felessmartket_be.domain.dto.paymentDto.OrdersRequestDto;
import org.example.felessmartket_be.repository.OrdersRepository;
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

    public JSONObject confirmPayment(String paymentKey, String orderId, String amount) {
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
//            try (OutputStream outputStream = connection.getOutputStream()) {
//                outputStream.write(obj.toString().getBytes(StandardCharsets.UTF_8));
//            }

//            OutputStream outputStream = connection.getOutputStream();
//            outputStream.write(obj.toString().getBytes("UTF-8"));

            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(obj.toString().getBytes(StandardCharsets.UTF_8));
                outputStream.flush(); // 버퍼에 남은 데이터를 모두 보내기
            }

            int code = connection.getResponseCode();
            boolean isSuccess = code == 200;

            InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

            // 응답 데이터 파싱
            Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
            jsonObject = (JSONObject) parser.parse(reader);
            responseStream.close();

            if (isSuccess) {
                // 결제 승인 성공 시 데이터베이스 업데이트 및 Redis 삭제
                Orders order = ordersService.findByOrderId(orderId);
                if (order != null) {
                    order.setOrderStatus("PAID");
                    ordersRepository.save(order);
                    redisService.deleteValues(orderId);
                } else {
                    log.warn("Order {} not found in database", orderId);
                }
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


}
