package br.com.service.accountTransfer.repository;

import br.com.service.accountTransfer.models.dtos.NotificacaoRequestDTO;

public interface IBacenRepository {

    void notification(NotificacaoRequestDTO notificacao);
}
