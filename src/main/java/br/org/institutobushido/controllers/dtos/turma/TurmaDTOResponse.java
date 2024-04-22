package br.org.institutobushido.controllers.dtos.turma;

import br.org.institutobushido.controllers.dtos.turma.tutor.TutorDTOResponse;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record TurmaDTOResponse(
        String nome,
        TutorDTOResponse tutor,
        String endereco) {
}
