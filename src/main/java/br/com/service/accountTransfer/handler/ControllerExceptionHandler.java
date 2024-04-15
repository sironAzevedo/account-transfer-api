package br.com.service.accountTransfer.handler;

import br.com.service.accountTransfer.handler.exception.AccountNotFoundException;
import br.com.service.accountTransfer.handler.exception.BusinessException;
import br.com.service.accountTransfer.handler.exception.APINotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = { APINotFoundException.class, AccountNotFoundException.class })
    public StandardError emptyResultNotFound(RuntimeException e, HttpServletRequest request) {
        return StandardError.builder(HttpStatus.NOT_FOUND.value(), e.getMessage(), new Date());
    }

    @ResponseBody
    @ExceptionHandler(value = { BusinessException.class })
    public ResponseEntity<StandardError> errorBusiness(BusinessException e) {
        StandardError error = StandardError.builder(e.getStatus(), e.getMessage(), new Date());
        return new ResponseEntity<>(error, HttpStatus.valueOf(e.getStatus()));
    }
}
