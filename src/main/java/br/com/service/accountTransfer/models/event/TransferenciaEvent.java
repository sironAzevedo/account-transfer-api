package br.com.service.accountTransfer.models.event;

import br.com.service.accountTransfer.models.dtos.TransferenciaRequestDTO;

public record TransferenciaEvent(TransferenciaRequestDTO transfer) {
}
