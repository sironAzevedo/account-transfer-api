package br.com.service.accountTransfer.controller;

import br.com.service.accountTransfer.models.dtos.TransferenciaRequestDTO;
import br.com.service.accountTransfer.models.dtos.TransferenciaResponseDTO;
import br.com.service.accountTransfer.service.ITransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/transferencia")
public class TransferController {

    private final ITransferService transferService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(
            summary = "Realiza transferencia de valores entre contas",
            tags = "Transferencia"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação de campo"),
            @ApiResponse(responseCode = "422", description = "Erro de negocio"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public TransferenciaResponseDTO transfer(@Valid @RequestBody TransferenciaRequestDTO request) {
        log.info("INICIO - [Transferencia de saldo da conta {} para {}]", request.getConta().getIdOrigem(), request.getConta().getIdDestino());
        var result = transferService.transferBalance(request);
        log.info("FIM - [Transferencia de saldo da conta {} para {}]", request.getConta().getIdOrigem(), request.getConta().getIdDestino());
        return result;
    }
}
