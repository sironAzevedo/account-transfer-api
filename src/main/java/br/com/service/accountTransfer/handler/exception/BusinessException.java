package br.com.service.accountTransfer.handler.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {
    private final int status;
    private static final long serialVersionUID = 1L;

    public BusinessException(HttpStatus status, String message) {
        super(message);
        this.status = status.value();
    }
}
