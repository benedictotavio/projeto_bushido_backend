package br.org.institutobushido.dtos.aluno.objects.historico_de_saude.informacoes_de_saude.alergia;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record AlergiaDTOResponse(boolean resposta, String tipo) {
}
