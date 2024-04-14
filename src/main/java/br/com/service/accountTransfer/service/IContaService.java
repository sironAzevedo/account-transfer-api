package br.com.service.accountTransfer.service;

import br.com.service.accountTransfer.dtos.ContaResponseDTO;
import br.com.service.accountTransfer.dtos.SaldoRequestDTO;

public interface IContaService {

    ContaResponseDTO getContaById(String idConta);

    void transferBalance(SaldoRequestDTO saldo);
}
