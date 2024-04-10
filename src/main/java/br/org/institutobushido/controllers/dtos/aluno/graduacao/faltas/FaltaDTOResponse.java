package br.org.institutobushido.controllers.dtos.aluno.graduacao.faltas;

public record FaltaDTOResponse(
    String motivo,
    String observacao,
    String data
) {
}
