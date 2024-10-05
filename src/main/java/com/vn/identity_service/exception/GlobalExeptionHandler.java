package com.vn.identity_service.exception;

import com.vn.identity_service.dto.request.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExeptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse<String>> handlingRuntimeException(RuntimeException e) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setMessage(e.getMessage());
        apiResponse.setCode(403);
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppExeption.class)
    ResponseEntity<ApiResponse<String>> handlingRuntimeException(AppExeption e) {
        ErrorCode errorCode = e.getErrorCode();
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setMessage(errorCode.getMessage());
        apiResponse.setCode(errorCode.getCode());
        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException e) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(
                ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<String>> handlingRuntimeException(MethodArgumentNotValidException e) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        ErrorCode errorCode = ErrorCode.valueOf(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
        apiResponse.setMessage(errorCode.getMessage());
        apiResponse.setCode(errorCode.getCode());
        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }
}
