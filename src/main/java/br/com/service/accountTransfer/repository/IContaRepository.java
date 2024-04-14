package br.com.service.accountTransfer.repository;

import br.com.service.accountTransfer.dtos.ContaResponseDTO;
import br.com.service.accountTransfer.dtos.SaldoRequestDTO;

import java.util.Optional;

public interface IContaRepository {

    Optional<ContaResponseDTO> getById(String idConta);

    void transferBalance(SaldoRequestDTO saldo);
}
