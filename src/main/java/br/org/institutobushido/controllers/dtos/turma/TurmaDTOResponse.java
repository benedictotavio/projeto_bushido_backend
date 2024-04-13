package br.org.institutobushido.controllers.dtos.turma;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record TurmaDTOResponse(
        String nome,
        String tutor,
        String endereco) {
}
