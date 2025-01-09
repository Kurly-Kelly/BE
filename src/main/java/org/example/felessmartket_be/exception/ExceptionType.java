package org.example.felessmartket_be.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum ExceptionType {
    NOT_FIND_PRODUCT("조회된 상품이 없습니다.");
    String message;
}
