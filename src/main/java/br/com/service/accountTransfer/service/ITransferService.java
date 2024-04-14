package br.com.service.accountTransfer.service;

import br.com.service.accountTransfer.dtos.TransferenciaRequestDTO;
import br.com.service.accountTransfer.dtos.TransferenciaResponseDTO;

public interface ITransferService {
    TransferenciaResponseDTO transferBalance(TransferenciaRequestDTO transfer);
}
