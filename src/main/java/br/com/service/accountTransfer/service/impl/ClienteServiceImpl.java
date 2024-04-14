package br.com.service.accountTransfer.service.impl;

import br.com.service.accountTransfer.dtos.ClienteResponseDTO;
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
    @Cacheable(value = "account-transfer:dadosClientePorCodigo", key = "#idConta", unless="#result == null")
    public ClienteResponseDTO getById(String idConta) {
        return clienteRepository.getById(idConta)
                .orElse(null);
    }
}
