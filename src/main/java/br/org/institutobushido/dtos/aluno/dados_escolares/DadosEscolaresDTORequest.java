package br.org.institutobushido.dtos.aluno.dados_escolares;

import br.org.institutobushido.enums.aluno.Turno;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record DadosEscolaresDTORequest(Turno turno, String escola, String serie) {}
