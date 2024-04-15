package br.com.service.accountTransfer.service.impl;

import br.com.service.accountTransfer.dtos.ContaResponseDTO;
import br.com.service.accountTransfer.dtos.SaldoRequestDTO;
import br.com.service.accountTransfer.repository.IContaRepository;
import br.com.service.accountTransfer.service.IContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContaServiceImpl implements IContaService {

    private final IContaRepository contaRepository;

    @Override
    @Cacheable(value = "account-transfer:dadosContaPorCodigo", key = "#idConta", unless="#result == null")
    public ContaResponseDTO getContaById(String idConta) {
        return contaRepository.getById(idConta)
                .orElse(null);
    }

    @Override
    @CacheEvict(value = "account-transfer:dadosContaPorCodigo", key = "#saldo.conta.idOrigem")
    public void transferBalance(SaldoRequestDTO saldo) {
        contaRepository.transferBalance(saldo);
    }
}
