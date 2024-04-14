package br.com.service.accountTransfer.service.impl;


import br.com.service.accountTransfer.config.AbstractTest;
import br.com.service.accountTransfer.dtos.ContaResponseDTO;
import br.com.service.accountTransfer.dtos.NotificacaoRequestDTO;
import br.com.service.accountTransfer.dtos.SaldoRequestDTO;
import br.com.service.accountTransfer.dtos.TransferenciaRequestDTO;
import br.com.service.accountTransfer.handler.exception.AccountNotFoundException;
import br.com.service.accountTransfer.handler.exception.BusinessException;
import br.com.service.accountTransfer.handler.exception.ClientNotFoundException;
import br.com.service.accountTransfer.service.IBacenService;
import br.com.service.accountTransfer.service.IClienteService;
import br.com.service.accountTransfer.service.IContaService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static br.com.service.accountTransfer.mock.MockTest.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TransferServiceImplTest extends AbstractTest {

    private AutoCloseable autoCloseable;

    @Mock
    private IClienteService clienteService;

    @Mock
    private IContaService contaService;

    @Mock
    private IBacenService bacenService;

    @InjectMocks
    private TransferServiceImpl transferService;

    @BeforeEach
    public void init() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        transferService = new TransferServiceImpl(clienteService, contaService, bacenService);
    }

    @AfterEach
    public void afterEach() throws Exception {
        autoCloseable.close();
    }

    @Test
    @DisplayName("Deve retornar exception quando dto null")
    public void deveRetornarExceptionQaundoDtoNull() {
        assertThrows(NullPointerException.class, () -> transferService.transferBalance(null));
    }

    @Test
    @DisplayName("Deve efeturar a transferencia com sucesso")
    public void deveEfeturarTranferenciaComSucesso() {
        when(clienteService.getById(anyString())).thenReturn(clienteResponse());
        when(contaService.getContaById(anyString())).thenReturn(contaResponse());
        doNothing().when(contaService).transferBalance(any(SaldoRequestDTO.class));
        doNothing().when(bacenService).notification(any(NotificacaoRequestDTO.class));

        var res = transferService.transferBalance(transferRequest());

        assertNotNull(res);
        verify(clienteService, times(1)).getById(anyString());
        verify(contaService, times(1)).getContaById(anyString());
        verify(contaService, times(1)).transferBalance(any(SaldoRequestDTO.class));
        verify(bacenService, times(1)).notification(any(NotificacaoRequestDTO.class));
    }

    @Test
    @DisplayName("Deve lancar exception quando cliente não encontrado")
    public void deveLancarExceptionQuandoClienteNotFound() {
        when(clienteService.getById(anyString())).thenReturn(null);
        assertThrows(ClientNotFoundException.class, () -> transferService.transferBalance(transferRequest()));
    }

    @Test
    @DisplayName("Deve lancar exception quando conta não encontrado")
    public void deveLancarExceptionQuandoContaNotFound() {
        when(clienteService.getById(anyString())).thenReturn(clienteResponse());
        when(contaService.getContaById(anyString())).thenReturn(null);
        assertThrows(AccountNotFoundException.class, () -> transferService.transferBalance(transferRequest()));
    }

    @Test
    @DisplayName("Deve lancar exception quando conta não ativa")
    public void deveLancarExceptionQuandoContaInativa() {
        when(clienteService.getById(anyString())).thenReturn(clienteResponse());

        ContaResponseDTO contaResponse = contaResponse();
        contaResponse.setAtivo(false);
        when(contaService.getContaById(anyString())).thenReturn(contaResponse);
        assertThrows(BusinessException.class, () -> transferService.transferBalance(transferRequest()));
    }

    @Test
    @DisplayName("Deve lancar exception quando saldo insuficiente")
    public void deveLancarExceptionQuandoSaldoInsuficiente() {
        when(clienteService.getById(anyString())).thenReturn(clienteResponse());

        ContaResponseDTO contaResponse = contaResponse();
        contaResponse.setSaldo(BigDecimal.TEN);

        when(contaService.getContaById(anyString())).thenReturn(contaResponse);
        assertThrows(BusinessException.class, () -> transferService.transferBalance(transferRequest()));
    }

    @Test
    @DisplayName("Deve lancar exception quando limite diário excedido")
    public void deveLancarExceptionQuandoLimiteDiarioExcedido() {
        when(clienteService.getById(anyString())).thenReturn(clienteResponse());
        when(contaService.getContaById(anyString())).thenReturn(contaResponse());

        TransferenciaRequestDTO requestDTO = transferRequest();
        requestDTO.setValor(BigDecimal.valueOf(1000));
        assertThrows(BusinessException.class, () -> transferService.transferBalance(requestDTO));
    }
}