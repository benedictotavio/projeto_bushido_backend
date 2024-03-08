package br.org.institutobushido.controllers.dtos.aluno.responsavel;

import com.mongodb.lang.NonNull;

import br.org.institutobushido.enums.aluno.FiliacaoResposavel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Valid
@Builder(setterPrefix = "with")
public record ResponsavelDTORequest(
        @NotNull(message = "Nome do responsavel é obrigatório") String nome,

        @NotNull(message = "Cpf do responsavel é obrigatório") @Pattern(regexp = "^\\d{11}$", message = "Cpf inválido!") String cpf,

        @NonNull() @Pattern(regexp = "^\\(?(\\d{2})\\)?[-\\s]?(\\d{4,5})[-\\s]?(\\d{4})$", message = "Formato de telefone inválido!") String telefone,

        String email,

        @NotNull(message = "Filiacao é obrigatório") FiliacaoResposavel filiacao) {
}
