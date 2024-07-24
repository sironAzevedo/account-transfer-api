package br.com.service.accountTransfer.models.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class SaldoRequestDTO extends NotificacaoRequestDTO{

    private String nomeDestino;
}
