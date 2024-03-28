package br.org.institutobushido.mappers.turma;

import br.org.institutobushido.controllers.dtos.turma.TurmaDTORequest;
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
}
