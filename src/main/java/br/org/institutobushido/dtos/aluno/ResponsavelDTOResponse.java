package br.org.institutobushido.dtos.aluno;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record ResponsavelDTOResponse(
        String nome,
        String cpf,
        String telefone,
        String email,
        String filiacao
){}
