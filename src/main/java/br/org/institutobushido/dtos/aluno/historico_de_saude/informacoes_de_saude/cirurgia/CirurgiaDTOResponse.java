package br.org.institutobushido.dtos.aluno.historico_de_saude.informacoes_de_saude.cirurgia;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record CirurgiaDTOResponse(boolean resposta, String tipo) {
}
