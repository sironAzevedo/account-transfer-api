package br.com.service.accountTransfer.repository.impl;

import br.com.service.accountTransfer.dtos.ClienteResponseDTO;
import br.com.service.accountTransfer.repository.IClienteRepository;
import br.com.service.accountTransfer.repository.client.ClientAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClienteRepositoryImpl implements IClienteRepository {

    private final ClientAPI client;

    @Override
    public Optional<ClienteResponseDTO> getById(String idCliente) {
        return Optional.ofNullable(client.getById(idCliente));
    }
}
