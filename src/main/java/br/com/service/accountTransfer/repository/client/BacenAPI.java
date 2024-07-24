package br.com.service.accountTransfer.repository.client;

import br.com.service.accountTransfer.models.dtos.NotificacaoRequestDTO;
import br.com.service.accountTransfer.infra.integracao.CustomErrorDecoder;
import br.com.service.accountTransfer.infra.integracao.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "bacen", url = "${api.bacen.notificacao.url}",
        configuration = { FeignConfig.class, CustomErrorDecoder.class })
public interface BacenAPI {

    @PostMapping
    void notification(NotificacaoRequestDTO requestDTO);
}
