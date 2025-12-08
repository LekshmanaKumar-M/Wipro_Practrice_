package com.myfin.exception;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException(String msg) {
        super(msg);
    }
}
