package org.example.felessmartket_be.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    ExceptionType type;

    public CustomException(ExceptionType type) {
        super(type.getMessage());
        this.type = type;
    }


}
