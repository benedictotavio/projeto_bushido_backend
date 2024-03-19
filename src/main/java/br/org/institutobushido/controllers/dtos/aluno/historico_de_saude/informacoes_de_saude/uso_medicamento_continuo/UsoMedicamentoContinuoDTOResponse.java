package br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.uso_medicamento_continuo;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record UsoMedicamentoContinuoDTOResponse(boolean resposta, String tipo) {
}
