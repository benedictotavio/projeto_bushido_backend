package br.org.institutobushido.controllers.dtos.admin.login;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LoginDTOResponseTest {
    @Test
    void deveRetornarTokenESenhaDoAdmin() {
        String token = "valid_token";
        String role = "valid_role";
        LoginDTOResponse loginDTOResponse = new LoginDTOResponse(token, role);
        assertEquals(token, loginDTOResponse.token());
        assertEquals(role, loginDTOResponse.role());
    }
}
