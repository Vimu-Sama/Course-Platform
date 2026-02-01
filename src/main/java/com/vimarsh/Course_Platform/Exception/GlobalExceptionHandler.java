package com.vimarsh.Course_Platform.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "User already exists");
        error.put("message", ex.getMessage());
        error.put("timestamp", Instant.now().toString());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    public ResponseEntity<Map<String, String>> userBadRequestDTOError(UserBadRequestDTOError ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Bad Request");
        error.put("message", ex.getMessage());
        error.put("timestamp", Instant.now().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    public ResponseEntity<Map<String, String>> userBadRequestDTOError(UserDatabaseSaveException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Internal Server Error- Bad Gateway");
        error.put("message", ex.getMessage());
        error.put("timestamp", Instant.now().toString());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(error);
    }
}
