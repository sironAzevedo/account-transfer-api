package br.com.service.accountTransfer.infra.integracao;

import br.com.service.accountTransfer.handler.exception.APINotAvailableException;
import br.com.service.accountTransfer.handler.exception.BusinessException;
import br.com.service.accountTransfer.handler.exception.APINotFoundException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        return switch (response.status()) {
            case 400 -> throw new BusinessException(HttpStatus.BAD_REQUEST, "Error negocio");
            case 404 -> throw new APINotFoundException("not found");
            case 429 -> new RetryableException(
                    response.status(),
                    "Rate limit exceeded, retrying...",
                    response.request().httpMethod(),
                    Long.valueOf(3),
                    response.request()
            );
            case 503 -> throw new APINotAvailableException("Api is unavailable");
            default -> new Exception("Exception while getting product details");
        };
    }
}
