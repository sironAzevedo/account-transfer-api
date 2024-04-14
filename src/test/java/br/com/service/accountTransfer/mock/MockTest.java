package br.com.service.accountTransfer.mock;

import br.com.service.accountTransfer.dtos.*;

import java.math.BigDecimal;

public final class MockTest {

    private MockTest(){}

    public static TransferenciaRequestDTO transferRequest(){
        return TransferenciaRequestDTO.builder()
                .idCliente("2ceb26e9-7b5c-417e-bf75-ffaa66e3a76f")
                .valor(BigDecimal.valueOf(100))
                .conta(contaMock())
                .build();
    }

    public static ClienteResponseDTO clienteResponse(){
        ClienteResponseDTO cliente = new ClienteResponseDTO();
        cliente.setId("bcdd1048-a501-4608-bc82-66d7b4db3600");
        cliente.setNome("João Silva");
        cliente.setTelefone("912348765");
        cliente.setTipoPessoa("Fisica");
        return cliente;
    }

    public static ContaResponseDTO contaResponse() {
        ContaResponseDTO conta = new ContaResponseDTO();
        conta.setId("d0d32142-74b7-4aca-9c68-838aeacef96b");
        conta.setSaldo(BigDecimal.valueOf(5000));
        conta.setAtivo(true);
        conta.setLimiteDiario(BigDecimal.valueOf(500));

        return conta;
    }

    public static NotificacaoRequestDTO notificacaoRequest() {
        return NotificacaoRequestDTO.builder()
                .valor(BigDecimal.valueOf(100))
                .conta(contaMock())
                .build();
    }

    public static ContaTransferenciaDTO contaMock() {
        ContaTransferenciaDTO c = new ContaTransferenciaDTO();
        c.setIdOrigem("d0d32142-74b7-4aca-9c68-838aeacef96b");
        c.setIdDestino("41313d7b-bd75-4c75-9dea-1f4be434007f");
        return c;
    }
}
