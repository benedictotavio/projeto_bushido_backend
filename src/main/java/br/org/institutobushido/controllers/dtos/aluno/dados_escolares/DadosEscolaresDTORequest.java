package br.org.institutobushido.controllers.dtos.aluno.dados_escolares;

import br.org.institutobushido.providers.enums.aluno.Turno;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record DadosEscolaresDTORequest(

        @Pattern(regexp = "^(MANHA|TARDE|NOITE)$", message = "Turno inválido!")
        @NotNull(message = "Turno é obrigatório!")
        Turno turno,

        @NotNull(message = "Escola é obrigatório!")
        @NotEmpty(message = "Escola é obrigatório!")
        String escola,

        @NotNull(message = "Serie é obrigatório!")
        @NotEmpty(message = "Serie é obrigatório!")
        String serie) {
}
