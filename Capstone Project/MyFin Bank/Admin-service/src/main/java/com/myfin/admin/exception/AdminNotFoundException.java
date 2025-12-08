package com.myfin.admin.exception;

public class AdminNotFoundException extends RuntimeException {
    public AdminNotFoundException(String msg) {
        super(msg);
    }
}
