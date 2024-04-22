package br.org.institutobushido.controllers.dtos.aluno.responsavel;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record ResponsavelDTOResponse(
                String nome,
                String cpf,
                String telefone,
                String email,
                String filiacao) {
}