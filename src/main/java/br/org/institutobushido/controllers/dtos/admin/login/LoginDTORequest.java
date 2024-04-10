package br.org.institutobushido.controllers.dtos.admin.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record LoginDTORequest(
                @Email(message = "Formato de email inválido") @NotNull(message = "Email é obrigatório") @NotEmpty(message = "Email é obrigatório") String email,
                @NotNull(message = "Senha é obrigatório") @NotEmpty(message = "Senha é obrigatório") String senha) {
}