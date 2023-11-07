package com.ssafy.voyage.auth.exception;

public class NoSuchRefreshTokenInDBException extends RuntimeException {
    private final String message;

    public NoSuchRefreshTokenInDBException(String message) {
        this.message = message;
    }
}
