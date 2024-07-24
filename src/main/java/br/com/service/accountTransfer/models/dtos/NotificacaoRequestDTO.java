package br.com.service.accountTransfer.models.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class NotificacaoRequestDTO {

    private BigDecimal valor;
    private ContaTransferenciaDTO conta;

}
