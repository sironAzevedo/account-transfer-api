package br.com.service.accountTransfer.service;

import br.com.service.accountTransfer.models.dtos.NotificacaoRequestDTO;

public interface IBacenService {

    void notification(NotificacaoRequestDTO notificacao);
}
