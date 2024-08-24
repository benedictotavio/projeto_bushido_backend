package br.org.institutobushido.controllers.response.success;

import br.org.institutobushido.models.admin.turmas.TurmaResponsavel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SuccessLoginAuthenticatedTest {
    @Test
    void deveCriarSuccessLoginAuthenticated() {
        String token = "token";
        String role = "role";
        TurmaResponsavel turma = new TurmaResponsavel("Turma responsavel", "Turma 1");
        SuccessLoginAuthenticated successLoginAuthenticated = new SuccessLoginAuthenticated(token, role, List.of(turma));

        assertEquals(token, successLoginAuthenticated.getToken());
        assertEquals(role, successLoginAuthenticated.getRole());
        assertEquals(HttpStatus.OK.value(), successLoginAuthenticated.getStatus());
        assertTrue(successLoginAuthenticated.isSuccess());
    }

    @Test
    void test_initialize_success_login_authenticated_with_null_values() {
        String token = null;
        String role = null;
        TurmaResponsavel turma = new TurmaResponsavel("Turma responsavel", "Turma 1");
        SuccessLoginAuthenticated successLoginAuthenticated = new SuccessLoginAuthenticated(token, role, List.of(turma));

        assertNull(successLoginAuthenticated.getToken());
        assertNull(successLoginAuthenticated.getRole());
        assertEquals(HttpStatus.OK.value(), successLoginAuthenticated.getStatus());
        assertTrue(successLoginAuthenticated.isSuccess());
    }
}
