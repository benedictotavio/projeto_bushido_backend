package br.org.institutobushido.controllers.dtos.aluno.dados_escolares;

import br.org.institutobushido.enums.aluno.Turno;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record DadosEscolaresDTOResponse(Turno turno, String escola, String serie) {
}
