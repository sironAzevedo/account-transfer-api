package br.com.service.accountTransfer.repository;

import br.com.service.accountTransfer.dtos.NotificacaoRequestDTO;

public interface IBacenRepository {

    void notification(NotificacaoRequestDTO notificacao);
}
