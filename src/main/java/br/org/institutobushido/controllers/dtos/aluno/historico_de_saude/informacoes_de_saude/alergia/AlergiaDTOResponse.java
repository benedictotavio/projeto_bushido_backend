package br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.alergia;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record AlergiaDTOResponse(boolean resposta, String tipo) {
}
