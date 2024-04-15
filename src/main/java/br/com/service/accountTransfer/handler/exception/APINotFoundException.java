package br.com.service.accountTransfer.handler.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class APINotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public APINotFoundException(String message) {
        super(message);
    }
}
