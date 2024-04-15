package br.com.service.accountTransfer.service.impl;

import br.com.service.accountTransfer.dtos.*;
import br.com.service.accountTransfer.handler.exception.AccountNotFoundException;
import br.com.service.accountTransfer.handler.exception.BusinessException;
import br.com.service.accountTransfer.handler.exception.APINotFoundException;
import br.com.service.accountTransfer.service.IBacenService;
import br.com.service.accountTransfer.service.IClienteService;
import br.com.service.accountTransfer.service.IContaService;
import br.com.service.accountTransfer.service.ITransferService;
import lombok.NonNull;
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
    private final IBacenService bacenService;

    @Override
    @Transactional
    public TransferenciaResponseDTO transferBalance(@NonNull TransferenciaRequestDTO transfer) {

        log.info("Veririficando se o cliente {} existe.", transfer.getIdCliente());
        Optional.ofNullable(clienteService.getById(transfer.getIdCliente()))
                .orElseThrow(() -> new APINotFoundException("Client not found"));

        log.info("Buscando dados da conta {}.", transfer.getConta().getIdOrigem());
        var conta = Optional.ofNullable(contaService.getContaById(transfer.getConta().getIdOrigem()))
                .orElseThrow(() -> new AccountNotFoundException("Account origin not found"));

        log.info("Verificando se a transferencia pode ser realizada.");
        validTransfer(conta, transfer.getValor());

        log.info("Realizando a transferencia");
        realizarTransferencia(transfer);

        // Notificar o BACEN que a transação foi concluída com sucesso.
        log.info("Notificando que a transferencia foi realizando com sucesso ");
        notificarBacen(transfer);

        return TransferenciaResponseDTO.builder()
                .idTransferencia(UUID.randomUUID())
                .build();
    }

    // Validar se a conta corrente está ativa
    // Validar se o cliente tem saldo disponível na conta corrente para realizar a transferência
    // Validar se o limite diário do cliente é maior que zero e maior que o valor da transferência a ser realizada
    private void validTransfer(@NonNull ContaResponseDTO conta, @NonNull BigDecimal valor) {

        log.info("Verificando se a conta está ativa.");
        if (!conta.isAtivo()) throw new BusinessException(UNPROCESSABLE_ENTITY, "Conta não está ativa.");

        log.info("Verificando se a conta contém saldo suficiente.");
        if (conta.getSaldo().compareTo(valor) < 0) throw new BusinessException(UNPROCESSABLE_ENTITY,"Saldo insuficiente.");

        log.info("Verificando se o valor da transferencia está de acordo com o limite diário.");
        if (conta.getLimiteDiario().compareTo(valor) < 0) throw new BusinessException(UNPROCESSABLE_ENTITY,"Limite diário excedido.");
    }

    private void realizarTransferencia(@NonNull TransferenciaRequestDTO transfer) {
        var saldo = SaldoRequestDTO.builder()
                .valor(transfer.getValor())
                .conta(transfer.getConta())
                .build();

        contaService.transferBalance(saldo);
    }

    private void notificarBacen(@NonNull TransferenciaRequestDTO transfer) {
        var notification = NotificacaoRequestDTO.builder()
                .valor(transfer.getValor())
                .conta(transfer.getConta())
                .build();

        bacenService.notification(notification);
    }
}
