package com.ssafy.voyage.auth.exception;

public class NoAccessTokenException extends RuntimeException {
    public NoAccessTokenException(String message) {
        super(message);
    }
}
