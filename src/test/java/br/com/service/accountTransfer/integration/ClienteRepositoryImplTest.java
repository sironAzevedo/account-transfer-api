package br.com.service.accountTransfer.integration;

import br.com.service.accountTransfer.config.AbstractTest;
import br.com.service.accountTransfer.models.dtos.ClienteResponseDTO;
import br.com.service.accountTransfer.handler.exception.APINotFoundException;
import br.com.service.accountTransfer.repository.IClienteRepository;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClienteRepositoryImplTest extends AbstractTest {

    @Autowired
    private IClienteRepository clienteRepository;

    @Test
    public void testGetClienteById() {
        // Configurar o comportamento esperado do WireMock
        stubFor(WireMock.get(urlEqualTo("/clientes/bcdd1048-a501-4608-bc82-66d7b4db3600"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"id\": \"bcdd1048-a501-4608-bc82-66d7b4db3600\", \"nome\": \"João Silva\", \"telefone\": \"912348765\"}, \"tipoPessoa\": \"Fisica\"}")));

        // Chamando o método getById do repository
        Optional<ClienteResponseDTO> responseDTO = clienteRepository.getById("bcdd1048-a501-4608-bc82-66d7b4db3600");

        // Verificando se o cliente retornado é o esperado
        assertEquals("bcdd1048-a501-4608-bc82-66d7b4db3600", responseDTO.get().getId());
    }

    @Test
    public void testGetClienteByIdNotFound() {
        // Configurar o comportamento esperado do WireMock
        stubFor(WireMock.get(urlEqualTo("/clientes/123"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"id\": \"bcdd1048-a501-4608-bc82-66d7b4db3600\", \"nome\": \"João Silva\", \"telefone\": \"912348765\"}, \"tipoPessoa\": \"Fisica\"}")));

        // Chamando o método getById do repository
        assertThrows(APINotFoundException.class, () -> clienteRepository.getById("bcdd1048-a501-4608-bc82-66d7b4db3600"));
    }
}
