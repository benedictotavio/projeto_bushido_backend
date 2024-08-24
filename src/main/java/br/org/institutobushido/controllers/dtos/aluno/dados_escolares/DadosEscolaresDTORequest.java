package br.org.institutobushido.controllers.dtos.aluno.dados_escolares;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record DadosEscolaresDTORequest(

        @NotNull(message = "Escola é obrigatório!")
        @NotEmpty(message = "Escola é obrigatório!")
        String escola) {
}
