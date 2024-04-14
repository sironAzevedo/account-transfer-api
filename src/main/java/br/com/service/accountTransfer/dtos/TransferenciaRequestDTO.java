package br.com.service.accountTransfer.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferenciaRequestDTO {

    private String idCliente;
    private BigDecimal valor;
    private ContaDTO conta;

}
