package com.vimarsh.Course_Platform.Exception;

import com.vimarsh.Course_Platform.DataTransferObjects.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {
    private ApiError buildError(String error, String message) {
        return new ApiError(error, message);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(buildError("User already exists", ex.getMessage()));
    }

    @ExceptionHandler(UserBadRequestDTOError.class)
    public ResponseEntity<ApiError> handleBadRequest(UserBadRequestDTOError ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(buildError("Bad Request", ex.getMessage()));
    }

    @ExceptionHandler(UserDatabaseSaveException.class)
    public ResponseEntity<ApiError> handleDatabaseSave(UserDatabaseSaveException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(buildError("Internal Server Error - Bad Gateway", ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(buildError("Not Found", ex.getMessage()));
    }

    @ExceptionHandler(AlreadyEnrolledException.class)
    public ResponseEntity<ApiError> handleAlreadyEnrolled(AlreadyEnrolledException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(buildError("Already enrolled", ex.getMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiError> handleForbidden(ForbiddenException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(buildError("Forbidden", ex.getMessage()));
    }

    // ✅ Fallback for unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildError("Internal Server Error", ex.getMessage()));
    }
}
