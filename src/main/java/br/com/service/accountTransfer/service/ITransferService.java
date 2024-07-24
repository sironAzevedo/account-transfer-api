package br.com.service.accountTransfer.service;

import br.com.service.accountTransfer.models.dtos.TransferenciaRequestDTO;
import br.com.service.accountTransfer.models.dtos.TransferenciaResponseDTO;

public interface ITransferService {
    TransferenciaResponseDTO transferBalance(TransferenciaRequestDTO transfer);
}
