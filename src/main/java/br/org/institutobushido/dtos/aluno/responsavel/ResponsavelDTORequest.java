package br.org.institutobushido.dtos.aluno.responsavel;

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

        @NotNull(message = "Cpf do responsavel é obrigatório") @Pattern(regexp = "\\^d{11}$", message = "Cpf inválido!") String cpf,

        @NonNull() @Pattern(regexp = "^\\(?[1-9]{2}\\)? ?(\\d{4,5})\\-?\\d{4}", message = "Formato de telefone inválido!") String telefone,

        String email,

        @NotNull(message = "Filiacao é obrigatório") @Pattern(regexp = "^(PAI|MAE|OUTRO)$", message = "Filiacao inválida!") FiliacaoResposavel filiacao) {
}
