package br.com.service.accountTransfer.handler.exception;

import lombok.Getter;

@Getter
public class AccountNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AccountNotFoundException(String message) {
        super(message);
    }
}
