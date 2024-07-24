package br.com.service.accountTransfer.models.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class TransferenciaResponseDTO {

    private UUID idTransferencia;
}
