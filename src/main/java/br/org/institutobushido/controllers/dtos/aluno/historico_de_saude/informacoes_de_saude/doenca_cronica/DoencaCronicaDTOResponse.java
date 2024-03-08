package br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.doenca_cronica;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record DoencaCronicaDTOResponse(boolean resposta, String tipo) {
}
