package br.org.institutobushido.controllers.dtos.aluno.dados_escolares;

import br.org.institutobushido.enums.aluno.Turno;

public record UpdateDadosEscolaresDTORequest(
    Turno turno,
    String escola,
    String serie
) {
}
