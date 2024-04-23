package br.org.institutobushido.controllers.dtos.turma;

import java.util.List;

public record DadosTurmaDTOResponse(
        String email,
        List<TurmaAlunoDTOResponse> alunos) {
}
