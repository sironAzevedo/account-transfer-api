package br.com.service.accountTransfer.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class TransferenciaRequestDTO {

    private String idCliente;
    private BigDecimal valor;
    private ContaTransferenciaDTO conta;

}
