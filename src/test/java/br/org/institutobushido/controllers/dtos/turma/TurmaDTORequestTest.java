package br.org.institutobushido.controllers.dtos.turma;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TurmaDTORequestTest {
    @Test
    public void test_create_new_instance_with_valid_parameters() {
        TurmaDTORequest turmaDTORequest = new TurmaDTORequest("Nome", "Tutor", "Endereço");
        assertNotNull(turmaDTORequest);
        assertEquals("Nome", turmaDTORequest.nome());
        assertEquals("Tutor", turmaDTORequest.tutor());
        assertEquals("Endereço", turmaDTORequest.endereco());
    }
}
