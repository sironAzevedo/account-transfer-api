package br.com.service.accountTransfer.repository.client;

import br.com.service.accountTransfer.dtos.ClienteResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "clientAPI", url = "${api.client.url}")
public interface ClientAPI {

    @GetMapping("/{idCliente}")
    ClienteResponseDTO getById(@PathVariable("idCliente") String idCliente);
}
