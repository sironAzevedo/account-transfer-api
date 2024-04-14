package br.com.service.accountTransfer.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificacaoRequestDTO {

    private double valor;
    private ContaDTO conta;

}
