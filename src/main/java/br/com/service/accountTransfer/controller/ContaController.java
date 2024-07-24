package br.com.service.accountTransfer.controller;

import br.com.service.accountTransfer.models.dtos.ContaResponseDTO;
import br.com.service.accountTransfer.infra.log.Loggable;
import br.com.service.accountTransfer.service.IContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/conta")
public class ContaController {

    private final IContaService contaService;

    @GetMapping("/{codigo}")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(
            summary = "consultar contas",
            tags = "contas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação de campo"),
            @ApiResponse(responseCode = "422", description = "Erro de negocio"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @Loggable
    public ContaResponseDTO getConta(@PathVariable("codigo") String codigo) {
        return contaService.getContaById(codigo);
    }

    @GetMapping("saldo/{saldo}")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(
            summary = "consultar contas",
            tags = "contas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação de campo"),
            @ApiResponse(responseCode = "422", description = "Erro de negocio"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ContaResponseDTO getContaBySaldo(@PathVariable("saldo") String codigo) {
        log.info("INICIO - [consulta dados da conta por saldo {}]", codigo);
        var result = contaService.getSaldo(codigo);
        log.info("FIM - [consulta dados da conta por saldo {}]", codigo);
        return result;
    }
}
