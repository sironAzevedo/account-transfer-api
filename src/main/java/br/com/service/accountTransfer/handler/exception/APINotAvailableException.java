package br.com.service.accountTransfer.handler.exception;

import java.io.Serial;

public class APINotAvailableException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public APINotAvailableException(String message) {
        super(message);
    }
}
