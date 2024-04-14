package br.com.service.accountTransfer.service;

import br.com.service.accountTransfer.dtos.ClienteResponseDTO;
import br.com.service.accountTransfer.dtos.ContaResponseDTO;

public interface IContaService {

    ContaResponseDTO getContaById(String idConta);
}
