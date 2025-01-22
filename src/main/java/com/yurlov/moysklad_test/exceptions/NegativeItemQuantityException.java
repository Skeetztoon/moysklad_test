package com.yurlov.moysklad_test.exceptions;

public class NegativeItemQuantityException extends RuntimeException {
    public NegativeItemQuantityException(String message) {
        super(message);
    }
}
