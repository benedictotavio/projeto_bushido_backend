package br.org.institutobushido.mappers.turma;

import java.util.List;
import java.util.stream.Collectors;

import br.org.institutobushido.controllers.dtos.turma.TurmaDTORequest;
import br.org.institutobushido.controllers.dtos.turma.TurmaDTOResponse;
import br.org.institutobushido.model.turma.Turma;

public class TurmaMapper {
    public static Turma mapToTurma(TurmaDTORequest turmaDTORequest) {
        if (turmaDTORequest == null) {
            return null;
        }
        return new Turma(turmaDTORequest.endereco(), turmaDTORequest.nome(), turmaDTORequest.tutor());
    }

    private TurmaMapper() {
    }

    public static TurmaDTOResponse mapToTurmaDTOResponse(Turma turma) {
        if (turma == null) {
            return null;
        }
        return TurmaDTOResponse.builder()
                .withEndereco(turma.getEndereco())
                .withNome(turma.getNome())
                .withTutor(turma.getTutor())
                .withAlunos(AlunoMapper.mapToListAlunoDTOResponse(turma.getAlunos()))
                .build();
    }

    public static List<TurmaDTOResponse> mapToListTurmaDTOResponse(List<Turma> turmas) {
        return turmas.stream().map(TurmaMapper::mapToTurmaDTOResponse).collect(Collectors.toList());
    }
}
