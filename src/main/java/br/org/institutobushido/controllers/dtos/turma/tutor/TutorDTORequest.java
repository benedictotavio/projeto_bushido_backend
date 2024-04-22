package br.org.institutobushido.controllers.dtos.turma.tutor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TutorDTORequest(
        @NotEmpty(message = "Nome é obrigatório") @NotNull(message = "Nome é obrigatório") String nome,
        @Email(message = "Formato de email inválido") @NotNull(message = "Email é obrigatório") String email) {
}
