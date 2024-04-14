package br.com.service.accountTransfer.repository.client;

import br.com.service.accountTransfer.dtos.ClienteResponseDTO;
import br.com.service.accountTransfer.dtos.ContaResponseDTO;
import br.com.service.accountTransfer.dtos.TransferenciaRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "accountAPI", url = "${api.account.url}")
public interface AccountAPI {

    @GetMapping("/{idConta}")
    ContaResponseDTO getById(@PathVariable("idConta") String idConta);

    void updateSaldo(TransferenciaRequestDTO requestDTO);
}
