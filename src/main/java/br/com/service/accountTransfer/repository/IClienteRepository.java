package br.com.service.accountTransfer.repository;

import br.com.service.accountTransfer.dtos.ClienteResponseDTO;

import java.util.Optional;

public interface IClienteRepository {

    Optional<ClienteResponseDTO> getById(String idCliente);
}
