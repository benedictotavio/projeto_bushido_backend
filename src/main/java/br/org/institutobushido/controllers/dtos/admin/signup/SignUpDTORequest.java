package br.org.institutobushido.controllers.dtos.admin.signup;

import br.org.institutobushido.enums.admin.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record SignUpDTORequest(
                @NotNull(message = "Nome é obrigatório") @NotEmpty(message = "Nome é obrigatório") String nome,
                @Email(message = "Formato de email inválido") @NotNull(message = "Email é obrigatório") String email,
                @NotNull(message = "Senha é obrigatório") @NotEmpty(message = "Senha é obrigatório") String senha,
                @NotNull(message = "Cargo é obrigatório") @NotEmpty(message = "Cargo é obrigatório") String cargo,
                @NotNull(message = "Role é obrigatório") @Pattern(regexp = "ADMIN|USER") UserRole role) {
}