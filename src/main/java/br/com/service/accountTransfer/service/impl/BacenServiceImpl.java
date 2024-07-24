package br.com.service.accountTransfer.service.impl;

import br.com.service.accountTransfer.models.dtos.NotificacaoRequestDTO;
import br.com.service.accountTransfer.repository.IBacenRepository;
import br.com.service.accountTransfer.service.IBacenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BacenServiceImpl implements IBacenService {

    private final IBacenRepository bacenRepository;

    @Override
    public void notification(NotificacaoRequestDTO notificacao) {
        bacenRepository.notification(notificacao);
    }
}
