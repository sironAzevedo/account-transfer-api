package br.com.service.accountTransfer.repository;

import br.com.service.accountTransfer.dtos.ContaResponseDTO;

import java.util.Optional;

public interface IContaRepository {

    Optional<ContaResponseDTO> getById(String idConta);
}
