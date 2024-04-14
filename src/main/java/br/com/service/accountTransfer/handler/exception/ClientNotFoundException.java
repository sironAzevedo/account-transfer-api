package br.com.service.accountTransfer.handler.exception;

import lombok.Getter;

@Getter
public class ClientNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ClientNotFoundException(String message) {
        super(message);
    }
}
