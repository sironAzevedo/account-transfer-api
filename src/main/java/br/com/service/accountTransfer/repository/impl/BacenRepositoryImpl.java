package br.com.service.accountTransfer.repository.impl;

import br.com.service.accountTransfer.models.dtos.NotificacaoRequestDTO;
import br.com.service.accountTransfer.repository.IBacenRepository;
import br.com.service.accountTransfer.repository.client.BacenAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BacenRepositoryImpl implements IBacenRepository {

    private final BacenAPI bacenAPI;

    @Override
    public void notification(NotificacaoRequestDTO notificacao) {
        bacenAPI.notification(notificacao);
    }
}
