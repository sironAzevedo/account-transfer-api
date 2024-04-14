package br.com.service.accountTransfer.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class SaldoRequestDTO extends NotificacaoRequestDTO{

    private String nomeDestino;
}
