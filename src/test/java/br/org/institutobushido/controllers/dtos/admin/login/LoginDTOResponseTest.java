package br.org.institutobushido.controllers.dtos.admin.login;

import br.org.institutobushido.models.admin.turmas.TurmaResponsavel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LoginDTOResponseTest {
    @Test
    void deveRetornarTokenESenhaDoAdmin() {
        String token = "valid_token";
        String role = "valid_role";
        TurmaResponsavel turma = new TurmaResponsavel("Turma responsavel", "Turma 1");
        LoginDTOResponse loginDTOResponse = new LoginDTOResponse(token, role, List.of(turma));
        assertEquals(token, loginDTOResponse.token());
        assertEquals(role, loginDTOResponse.role());
    }
}
