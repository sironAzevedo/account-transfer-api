package br.com.service.accountTransfer.service;

import br.com.service.accountTransfer.models.dtos.ContaResponseDTO;
import br.com.service.accountTransfer.models.dtos.SaldoRequestDTO;

public interface IContaService {

    ContaResponseDTO getContaById(String idConta);

    ContaResponseDTO getSaldo(String saldo);

    void transferBalance(SaldoRequestDTO saldo);
}
