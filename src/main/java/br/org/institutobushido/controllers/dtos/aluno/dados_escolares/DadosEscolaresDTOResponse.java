package br.org.institutobushido.controllers.dtos.aluno.dados_escolares;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record DadosEscolaresDTOResponse(
                String escola) {
}
