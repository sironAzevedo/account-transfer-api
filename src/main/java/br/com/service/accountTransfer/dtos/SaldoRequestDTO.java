package br.com.service.accountTransfer.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaldoRequestDTO {

    private double valor;
    private String nomeDestino;
}
