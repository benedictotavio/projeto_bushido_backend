package br.org.institutobushido.providers.mappers.turma;

import br.org.institutobushido.controllers.dtos.turma.TurmaDTORequest;
import br.org.institutobushido.controllers.dtos.turma.TurmaDTOResponse;
import br.org.institutobushido.controllers.dtos.turma.tutor.TutorDTORequest;
import br.org.institutobushido.controllers.dtos.turma.tutor.TutorDTOResponse;
import br.org.institutobushido.models.turma.Turma;
import br.org.institutobushido.models.turma.tutor.Tutor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

@SpringBootTest
class TurmaMapperTest {
    private Turma turma;
    private TurmaDTORequest turmaDTORequest;
    private TurmaDTOResponse turmaDTOResponse;

    @BeforeEach
    void setUp() {
        turma = new Turma("Endereço", "Nome", new Tutor("Tutor", "Tutor@email.com"));
        turmaDTORequest = new TurmaDTORequest("Endereço", new TutorDTORequest("Tutor", "Tutor@email.com"), "Tutor");
        turmaDTOResponse = new TurmaDTOResponse("Endereço", new TutorDTOResponse("Tutor", "Tutor@email.com"), "Tutor", LocalDate.now());
    }

    @Test
    void deveMapearTurmaParaTurmaDTOResponse() {
        turmaDTOResponse = TurmaMapper.mapToTurmaDTOResponse(turma);
        assertEquals(turmaDTOResponse.nome(), turma.getNome());
        assertEquals(turmaDTOResponse.endereco(), turma.getEndereco());
        assertEquals(turmaDTOResponse.tutor().email(), turma.getTutor().getEmail());
        assertEquals(turmaDTOResponse.tutor().nome(), turma.getTutor().getNome());
    }

    @Test
    void deveMapearTurmaDtoRequestParaTurma() {
        turma = TurmaMapper.mapToTurma(turmaDTORequest);
        assertEquals(turma.getNome(), turmaDTORequest.nome());
        assertEquals(turma.getEndereco(), turmaDTORequest.endereco());
        assertEquals(turma.getTutor().getEmail(), turmaDTORequest.tutor().email());
        assertEquals(turma.getTutor().getNome(), turmaDTORequest.tutor().nome());
    }
}
