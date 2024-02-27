package br.org.institutobushido.dtos.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record AdminDTORequest(
        @NotNull(message = "Nome é obrigatório") String nome,
        @Email(message = "Formato de email inválido") @NotNull(message = "Email é obrigatório") String email,
        @NotNull(message = "Senha é obrigatório") String senha,
        @NotNull(message = "Cargo é obrigatório") String cargo) {
}