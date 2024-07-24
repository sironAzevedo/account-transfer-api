package br.com.service.accountTransfer.models.dtos;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDTO implements Serializable {

    private String id;
    private String nome;
    private String telefone;
    private String tipoPessoa;
}
