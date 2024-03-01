package br.org.institutobushido.dtos.aluno.graduacao.faltas;

import jakarta.validation.constraints.NotNull;

public record FaltaDTORequest(
    @NotNull(message = "Motivo é obrigatório")
    String motivo, 
    String observacao) {
}
