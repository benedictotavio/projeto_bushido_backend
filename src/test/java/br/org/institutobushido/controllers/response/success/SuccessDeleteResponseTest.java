package br.org.institutobushido.controllers.response.success;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SuccessDeleteResponseTest {
    private SuccessDeleteResponse response;
    private String id;
    private String message;

    @BeforeEach
    void setUp() {
        id = "123";
        message = "Success";
        response = new SuccessDeleteResponse(id, message);
    }

    @Test
    void deveCriarSuccessDeleteResponse() {
        assertEquals(id, response.getId());
        assertEquals(message, response.getMessage());
        assertTrue(response.isSuccess());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void deveCriarSuccessDeleteResponseComEntidade() {
        String entity = "Turma";

        response = new SuccessDeleteResponse(id, message, entity);

        assertEquals(id, response.getId());
        assertEquals(message, response.getMessage());
        assertTrue(response.isSuccess());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(entity, response.getEntity());
    }

    @Test
    void deveSetarValoresNaClasse() {
        response.setSuccess(false);
        response.setId("321");
        response.setMessage("Error");
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setEntity("Turma");

        assertFalse(response.isSuccess());
        assertEquals("321", response.getId());
        assertEquals("Error", response.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("Turma", response.getEntity());
    }

    @Test
    void deveCriarSuccessDeleteResponseComTodosOsParametros() {
        String entity = "Turma";

        response = new SuccessDeleteResponse(id, message, entity, HttpStatus.OK.value(), true);

        assertEquals(id, response.getId());
        assertEquals(message, response.getMessage());
        assertTrue(response.isSuccess());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(entity, response.getEntity());
    }
}
