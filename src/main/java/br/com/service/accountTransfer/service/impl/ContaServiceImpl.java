package br.com.service.accountTransfer.service.impl;

import br.com.service.accountTransfer.dtos.ContaResponseDTO;
import br.com.service.accountTransfer.repository.IContaRepository;
import br.com.service.accountTransfer.service.IContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContaServiceImpl implements IContaService {

    private final IContaRepository contaRepository;

    @Override
    public ContaResponseDTO getContaById(String idConta) {
        return contaRepository.getById(idConta)
                .orElse(null);
    }
}
