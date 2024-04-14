package br.com.service.accountTransfer.repository.client;

import br.com.service.accountTransfer.dtos.NotificacaoRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "bacen", url = "${api.bacen.notificacao.url}")
public interface BacenAPI {

    @PostMapping
    void notification(NotificacaoRequestDTO requestDTO);
}
