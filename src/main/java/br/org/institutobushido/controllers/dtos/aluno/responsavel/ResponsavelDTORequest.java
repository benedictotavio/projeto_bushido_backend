package br.org.institutobushido.controllers.dtos.aluno.responsavel;

import br.org.institutobushido.enums.aluno.FiliacaoResposavel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Valid
@Builder(setterPrefix = "with")
public record ResponsavelDTORequest(
                @NotNull(message = "Nome do responsavel é obrigatório") @NotEmpty(message = "Nome do responsavel é obrigatório") String nome,

                @NotNull(message = "Cpf do responsavel é obrigatório") @NotEmpty(message = "Cpf do responsavel é obrigatório") @Pattern(regexp = "^\\d{11}$", message = "Cpf inválido! Ex: 12345678910") String cpf,

                @NotNull(message = "Telefone do responsavel é obrigatório") @Pattern(regexp = "^\\d{11}$", message = "Formato de telefone inválido! Ex: 11999999999") String telefone,

                @Email(message = "Formato de email inválido") @NotNull(message = "Email do responsavel é obrigatório") String email,

                @NotNull(message = "Filiacao é obrigatório") FiliacaoResposavel filiacao) {
}
