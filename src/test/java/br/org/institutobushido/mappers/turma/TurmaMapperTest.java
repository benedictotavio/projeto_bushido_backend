package br.org.institutobushido.mappers.turma;

import br.org.institutobushido.controllers.dtos.turma.TurmaDTORequest;
import br.org.institutobushido.controllers.dtos.turma.TurmaDTOResponse;
import br.org.institutobushido.models.turma.Turma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TurmaMapperTest {
    private Turma turma;
    private TurmaDTORequest turmaDTORequest;
    private TurmaDTOResponse turmaDTOResponse;

    @BeforeEach
    void setUp() {
        turma = new Turma("Endereço", "Nome", "Tutor");
        turmaDTORequest = new TurmaDTORequest("Endereço", "Nome", "Tutor");
        turmaDTOResponse = new TurmaDTOResponse("Endereço", "Nome", "Tutor");
    }

    @Test
    void deveMapearTurmaParaTurmaDTOResponse() {
        turmaDTOResponse = TurmaMapper.mapToTurmaDTOResponse(turma);
        assertEquals(turmaDTOResponse.nome(), turma.getNome());
        assertEquals(turmaDTOResponse.endereco(), turma.getEndereco());
        assertEquals(turmaDTOResponse.tutor(), turma.getTutor());
    }

    @Test
    void deveMapearTurmaDtoRequestParaTurma() {
        turma = TurmaMapper.mapToTurma(turmaDTORequest);
        assertEquals(turma.getNome(), turmaDTORequest.nome());
        assertEquals(turma.getEndereco(), turmaDTORequest.endereco());
        assertEquals(turma.getTutor(), turmaDTORequest.tutor());
    }
}
