package br.com.service.accountTransfer.service.impl;

import br.com.service.accountTransfer.dtos.ContaResponseDTO;
import br.com.service.accountTransfer.dtos.SaldoRequestDTO;
import br.com.service.accountTransfer.repository.IContaRepository;
import br.com.service.accountTransfer.service.IContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
    @Cacheable(value = "account-transfer:dadosContaPorSaldo", key = "#saldo", unless="#result == null")
    public ContaResponseDTO getSaldo(String saldo) {
        ContaResponseDTO r = new ContaResponseDTO();
        r.setId("d0d32142-74b7-4aca-9c68-838aeacef96b");
        r.setSaldo(BigDecimal.TEN);
        r.setLimiteDiario(BigDecimal.TEN);
        r.setAtivo(true);
        return r;
    }

    @Override
    @CacheEvict(value = "account-transfer:dadosContaPorCodigo", key = "#saldo.conta.idOrigem")
    public void transferBalance(SaldoRequestDTO saldo) {
        contaRepository.transferBalance(saldo);
    }
}
