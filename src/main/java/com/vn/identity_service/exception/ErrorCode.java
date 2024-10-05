package com.vn.identity_service.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_EXISTED(403, "User already exists111", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(400, "Uncategorized exception", HttpStatus.BAD_GATEWAY),
    USERNAME_INVALID(400, "Username is invalid", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(400, "Password is invalid", HttpStatus.CONFLICT),
    UNAUTHENTICATED(401, "UNAUTHENTICATED", HttpStatus.NOT_FOUND),
    UNAUTHORIZED(403, "You do not have permision", HttpStatus.FORBIDDEN),
    ;
    private final int code;
    private final String message;
    private HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
