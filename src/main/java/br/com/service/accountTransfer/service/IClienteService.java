package br.com.service.accountTransfer.service;

import br.com.service.accountTransfer.dtos.ClienteResponseDTO;

public interface IClienteService {

    ClienteResponseDTO getContaById(String idConta);

}
