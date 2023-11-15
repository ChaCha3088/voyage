package com.ssafy.voyage.auth.exception;

public class NoSuchRefreshTokenInDBException extends RuntimeException {

    public NoSuchRefreshTokenInDBException(String message) {
        super(message);
    }
}
