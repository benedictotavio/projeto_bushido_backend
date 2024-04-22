package br.org.institutobushido.controllers.dtos.turma;

import br.org.institutobushido.controllers.dtos.turma.tutor.TutorDTORequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TurmaDTORequest(
                @NotNull(message = "Nome da turma é obrigatório") @NotEmpty(message = "Nome da turma é obrigatório") String nome,
                @NotNull(message = "Tutor da turma é obrigatório") TutorDTORequest tutor,
                @NotNull(message = "Endereço da turma é obrigatório") @NotEmpty(message = "Endereço da turma é obrigatório") String endereco) {
}