package br.org.institutobushido.controllers.response.success;

import br.org.institutobushido.models.admin.turmas.TurmaResponsavel;
import br.org.institutobushido.models.admin.turmas.TurmaResponsavel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SuccessLoginAuthenticatedTest {

    private SuccessLoginAuthenticated successLoginAuthenticated;

    @BeforeEach
    void setUp() {
        successLoginAuthenticated = new SuccessLoginAuthenticated("token", "role", List.of(new TurmaResponsavel("Turma responsavel", "Turma 1")));
    }

    @Test
    void deveInstanciarASuccessLoginAuthenticated() {
        successLoginAuthenticated.setSuccess(true);
        successLoginAuthenticated.setRole("user");
        successLoginAuthenticated.setToken("123456789");
        successLoginAuthenticated.setStatus(HttpStatus.OK.value());
        successLoginAuthenticated.setTurmas(new ArrayList<>());

        assertNotNull(successLoginAuthenticated);
        assertEquals("123456789", successLoginAuthenticated.getToken());
        assertEquals("user", successLoginAuthenticated.getRole());
        assertEquals(HttpStatus.OK.value(), successLoginAuthenticated.getStatus());
        assertEquals(new ArrayList<>(), successLoginAuthenticated.getTurmas());
        assertTrue(successLoginAuthenticated.isSuccess());
    }
}
