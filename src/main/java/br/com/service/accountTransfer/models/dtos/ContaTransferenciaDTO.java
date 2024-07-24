package br.com.service.accountTransfer.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaTransferenciaDTO {
    private String idOrigem;
    private String idDestino;
}
