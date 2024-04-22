package br.org.institutobushido.controllers.dtos.turma;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TurmaDTOResponseTest {
    @Test
    public void test_create_turma_dtoresponse_with_valid_parameters() {
        TurmaDTOResponse turmaDTOResponse = new TurmaDTOResponse("Nome", "Tutor", "Endereço");
        assertNotNull(turmaDTOResponse);
        assertEquals("Nome", turmaDTOResponse.nome());
        assertEquals("Tutor", turmaDTOResponse.tutor());
        assertEquals("Endereço", turmaDTOResponse.endereco());
    }
}
