package org.example.felessmartket_be.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisProperties redisProperties;

    @PostConstruct
    public void testRedisConnection() {
        try {
            // Redis 호스트와 포트 출력
            System.out.println("Redis 호스트: " + redisProperties.getHost());
            System.out.println("Redis 포트: " + redisProperties.getPort());

            // 데이터 저장
            redisTemplate.opsForValue().set("testKey", "testValue");

            // 데이터 조회
            String value = redisTemplate.opsForValue().get("testKey");
            System.out.println("Redis 연결 성공: " + value);
        } catch (Exception e) {
            System.err.println("Redis 연결 실패: " + e.getMessage());
        }
    }
}
