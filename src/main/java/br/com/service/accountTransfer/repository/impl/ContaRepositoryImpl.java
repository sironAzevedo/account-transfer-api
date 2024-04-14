package br.com.service.accountTransfer.repository.impl;

import br.com.service.accountTransfer.dtos.ContaResponseDTO;
import br.com.service.accountTransfer.dtos.SaldoRequestDTO;
import br.com.service.accountTransfer.repository.IContaRepository;
import br.com.service.accountTransfer.repository.client.AccountAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ContaRepositoryImpl implements IContaRepository {

    private final AccountAPI account;

    @Override
    public Optional<ContaResponseDTO> getById(String idConta) {
        return Optional.ofNullable(account.getById(idConta));
    }

    @Override
    public void transferBalance(SaldoRequestDTO saldo) {
        account.transferBalance(saldo);
    }
}
