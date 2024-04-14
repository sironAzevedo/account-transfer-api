package br.com.service.accountTransfer.service.impl;

import br.com.service.accountTransfer.dtos.ContaResponseDTO;
import br.com.service.accountTransfer.dtos.TransferenciaRequestDTO;
import br.com.service.accountTransfer.dtos.TransferenciaResponseDTO;
import br.com.service.accountTransfer.handler.exception.AccountNotFoundException;
import br.com.service.accountTransfer.handler.exception.BusinessException;
import br.com.service.accountTransfer.handler.exception.ClientNotFoundException;
import br.com.service.accountTransfer.service.IClienteService;
import br.com.service.accountTransfer.service.IContaService;
import br.com.service.accountTransfer.service.ITransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements ITransferService {

    private final IClienteService clienteService;
    private final IContaService contaService;

    @Override
    @Transactional
    public TransferenciaResponseDTO makeTransfer(TransferenciaRequestDTO transfer) {

        // Validar se o cliente que vai receber a transferência existe passando o idCliente na API de Cadastro
        Optional.ofNullable(clienteService.getContaById(transfer.getIdCliente()))
                .orElseThrow(() -> new ClientNotFoundException("Client not found"));

        // Buscar dados da conta origem passando idConta na API de Contas
        var conta = Optional.ofNullable(contaService.getContaById(transfer.getConta().getIdOrigem()))
                .orElseThrow(() -> new AccountNotFoundException("Conta origem not found"));

        validarTransferencia(conta, transfer.getValor());

        // Notificar o BACEN que a transação foi concluída com sucesso.

        return TransferenciaResponseDTO.builder()
                .idTransferencia(UUID.randomUUID())
                .build();
    }

    // Validar se a conta corrente está ativa
    // Validar se o cliente tem saldo disponível na conta corrente para realizar a transferência
    // Validar se o limite diário do cliente é maior que zero e maior que o valor da transferência a ser realizada
    private void validarTransferencia(ContaResponseDTO conta, BigDecimal valor) {
        if (!conta.isAtivo()) throw new BusinessException(UNPROCESSABLE_ENTITY, "Conta não está ativa.");
        if (conta.getSaldo().compareTo(valor) < 0) throw new BusinessException(UNPROCESSABLE_ENTITY,"Saldo insuficiente.");
        if (conta.getLimiteDiario().compareTo(valor) < 0) throw new BusinessException(UNPROCESSABLE_ENTITY,"Limite diário excedido.");
    }
}
