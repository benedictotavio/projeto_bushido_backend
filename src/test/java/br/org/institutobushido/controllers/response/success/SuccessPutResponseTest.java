package br.org.institutobushido.controllers.response.success;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class SuccessPutResponseTest {
    private SuccessPutResponse response;

    @BeforeEach
    void setUp() {
        response = new SuccessPutResponse("123", "Success");
    }

    @Test
    void deveRetornarSucessoDeUmPut() {
        response.setMessage("Test Success");
        response.setStatus(HttpStatus.OK.value());
        response.setEntity("Aluno");
        response.setId("321");

        assertEquals("321", response.getId());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Test Success", response.getMessage());
        assertEquals("Aluno", response.getEntity());
    }

    @Test
    void deveInstanciarTodosOsParametros() {
        response = new SuccessPutResponse("123", "Success", "Aluno");
        assertEquals("123", response.getId());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Success", response.getMessage());
        assertEquals("Aluno", response.getEntity());
    }
}
