package br.org.institutobushido.dtos.aluno.dados_escolares;

import br.org.institutobushido.enums.Turno;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record DadosEscolaresDTOResponse(Turno turno, String escola, String serie) {
}
