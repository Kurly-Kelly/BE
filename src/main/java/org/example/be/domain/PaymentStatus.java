package org.example.be.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum PaymentStatus {
    PENDING("결제 대기"),
    COMPLETED("결제 완료"),
    FAILED("결제 실패");

    String description;

    PaymentStatus(String description) {
        this.description = description;
    }
}
