package br.org.institutobushido.controllers.dtos.admin.login;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class LoginDTORequestTest {
    @Test
    void deveRetornarEmailESenha() {
        LoginDTORequest loginDTORequest = new LoginDTORequest("validemail@example.com", "password");
        assertNotNull(loginDTORequest);
        assertEquals("validemail@example.com", loginDTORequest.email());
        assertEquals("password", loginDTORequest.senha());
    }
}
