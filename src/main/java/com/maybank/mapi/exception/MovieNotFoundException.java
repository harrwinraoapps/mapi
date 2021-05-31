package com.maybank.mapi.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class MovieNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MovieNotFoundException(String message) {
        super(message);
    }

    public MovieNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
