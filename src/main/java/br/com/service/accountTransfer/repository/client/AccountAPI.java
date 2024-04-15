package br.com.service.accountTransfer.repository.client;

import br.com.service.accountTransfer.dtos.ClienteResponseDTO;
import br.com.service.accountTransfer.dtos.ContaResponseDTO;
import br.com.service.accountTransfer.dtos.SaldoRequestDTO;
import br.com.service.accountTransfer.dtos.TransferenciaRequestDTO;
import br.com.service.accountTransfer.infra.integracao.CustomErrorDecoder;
import br.com.service.accountTransfer.infra.integracao.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(value = "accountAPI", url = "${api.account.url}",
        configuration = { FeignConfig.class, CustomErrorDecoder.class })
public interface AccountAPI {

    @GetMapping("/{idConta}")
    ContaResponseDTO getById(@PathVariable("idConta") String idConta);

    @PutMapping("/saldos")
    void transferBalance(SaldoRequestDTO requestDTO);
}
