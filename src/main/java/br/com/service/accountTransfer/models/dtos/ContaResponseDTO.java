package br.com.service.accountTransfer.models.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ContaResponseDTO {

    private String id;
    private BigDecimal saldo;
    private BigDecimal limiteDiario;
    private boolean ativo;
}
