package br.com.service.accountTransfer.service.impl;

import br.com.service.accountTransfer.dtos.ClienteResponseDTO;
import br.com.service.accountTransfer.handler.exception.APINotFoundException;
import br.com.service.accountTransfer.repository.IClienteRepository;
import br.com.service.accountTransfer.service.IClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements IClienteService {

    private final IClienteRepository clienteRepository;

    @Override
    @Cacheable(value = "account-transfer:dadosClientePorCodigo", key = "#idCliente", unless="#result == null")
    public ClienteResponseDTO getById(String idCliente) {
        try {
            return clienteRepository.getById(idCliente)
                    .orElse(null);
        } catch (Exception e) {
            throw new APINotFoundException("Client " + e.getMessage());
        }
    }
}
