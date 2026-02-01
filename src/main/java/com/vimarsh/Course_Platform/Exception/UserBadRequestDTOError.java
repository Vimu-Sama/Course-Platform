package com.vimarsh.Course_Platform.Exception;

public class UserBadRequestDTOError extends RuntimeException {
    public UserBadRequestDTOError(String message) {
        super(message);
    }
}
