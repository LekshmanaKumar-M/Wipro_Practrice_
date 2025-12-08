package com.myfin.admin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class AdminGlobalExceptionHandler {

    // Admin Not Found Exception
    @ExceptionHandler(AdminNotFoundException.class)
    public ResponseEntity<?> handleAdminNotFound(AdminNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new Error(ex.getMessage(), 404));
    }

    // Runtime Exceptions (invalid credentials, etc.)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Error(ex.getMessage(), 400));
    }

    // Any other unexpected exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobal(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error("Server Error: " + ex.getMessage(), 500));
    }

    // Error Response
    record Error(String message, int status) {}
}
