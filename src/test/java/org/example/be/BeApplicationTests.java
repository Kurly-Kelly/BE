package org.example.be;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@SuppressWarnings("removal")
class BeApplicationTests {

    @MockBean
    private AmazonS3 amazonS3;  // AmazonS3 빈을 모킹

    @MockBean
    private RedisTemplate<String, Object> redisTemplate;  // RedisTemplate 모킹

    @MockBean
    private PasswordEncoder passwordEncoder;  // PasswordEncoder 모킹

    @Test
    void contextLoads() {
    }

}
