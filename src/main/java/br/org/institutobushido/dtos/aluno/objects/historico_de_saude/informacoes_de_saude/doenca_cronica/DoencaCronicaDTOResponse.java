package br.org.institutobushido.dtos.aluno.objects.historico_de_saude.informacoes_de_saude.doenca_cronica;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record DoencaCronicaDTOResponse(boolean resposta, String tipo) {
}
