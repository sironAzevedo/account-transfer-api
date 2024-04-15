package br.com.service.accountTransfer.integration;

import br.com.service.accountTransfer.config.AbstractTest;
import br.com.service.accountTransfer.dtos.NotificacaoRequestDTO;
import br.com.service.accountTransfer.mock.MockTest;
import br.com.service.accountTransfer.repository.IBacenRepository;
import br.com.service.accountTransfer.utils.JsonUtils;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class BacenRepositoryImplTest extends AbstractTest {

    @Autowired
    private IBacenRepository bacenRepository;

    @Test
    public void testNotification() {
        NotificacaoRequestDTO notificacaoRequestDTO = MockTest.notificacaoRequest();
        String objetcToJson = JsonUtils.objetcToJson(notificacaoRequestDTO);

        // Configurar o comportamento esperado do WireMock
        stubFor(WireMock.post(urlEqualTo("/notificacoes"))
                .withRequestBody(equalToJson(objetcToJson))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())));

        // Chamando o método getById do repository
        bacenRepository.notification(notificacaoRequestDTO);
    }
}
