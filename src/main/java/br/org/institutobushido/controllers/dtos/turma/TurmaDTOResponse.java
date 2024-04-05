package br.org.institutobushido.controllers.dtos.turma;

import java.util.List;

import br.org.institutobushido.controllers.dtos.turma.aluno.AlunoDTOResponse;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record TurmaDTOResponse(
        String nome,
        String tutor,
        String endereco,
        List<AlunoDTOResponse> alunos) {
}
