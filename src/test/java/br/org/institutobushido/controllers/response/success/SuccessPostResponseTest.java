package br.org.institutobushido.controllers.response.success;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SuccessPostResponseTest {
    private SuccessPostResponse response;

    @BeforeEach
    void setUp() {
        response = new SuccessPostResponse("123", "Sucesso");
    }

    @Test
    void deveRetornarSucessoDeUmPost() {
        response.setId("321");
        response.setMessage("Sucesso");
        response.setStatus(HttpStatus.OK.value());
        response.setEntity("Aluno");

        assertEquals("321", response.getId());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Sucesso", response.getMessage());
        assertNotNull(response.getEntity());
    }

    @Test
    void deveInstanciarTodosOsParametros() {
        response = new SuccessPostResponse("123", "Sucesso", "Aluno");
        assertEquals("123", response.getId());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Sucesso", response.getMessage());
        assertNotNull(response.getEntity());
    }
}
