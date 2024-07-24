package br.com.service.accountTransfer.service;

import br.com.service.accountTransfer.models.dtos.ClienteResponseDTO;

public interface IClienteService {

    ClienteResponseDTO getById(String idCliente);

}
