package org.example.be.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public enum Delivery {
    GENERAL_DELIVERY, // 일반배송
    EARLY_DELIVERY, // 새벽배송
    SELLER_DELIVERY }// 판매자 배송