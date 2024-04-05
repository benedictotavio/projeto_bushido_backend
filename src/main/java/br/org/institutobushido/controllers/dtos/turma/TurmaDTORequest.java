package br.org.institutobushido.controllers.dtos.turma;

import jakarta.validation.constraints.NotNull;

public record TurmaDTORequest(
                @NotNull(message = "Nome da turma é obrigatório") String nome,
                @NotNull(message = "Tutor da turma é obrigatório") String tutor,
                @NotNull(message = "Endereço da turma é obrigatório") String endereco) {
}
