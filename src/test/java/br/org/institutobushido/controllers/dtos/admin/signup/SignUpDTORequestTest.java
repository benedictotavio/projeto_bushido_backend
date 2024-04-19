package br.org.institutobushido.controllers.dtos.admin.signup;

import br.org.institutobushido.enums.admin.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SignUpDTORequestTest {
    @Test
    void test_createSignUpDTORequestWithValidData() {
        SignUpDTORequest signUpDTORequest = new SignUpDTORequest("John Doe", "john.doe@example.com", "password", "role", UserRole.ADMIN);
        assertNotNull(signUpDTORequest);
        assertEquals("John Doe", signUpDTORequest.nome());
        assertEquals("john.doe@example.com", signUpDTORequest.email());
        assertEquals("password", signUpDTORequest.senha());
        assertEquals("role", signUpDTORequest.cargo());
        assertEquals(UserRole.ADMIN, signUpDTORequest.role());
    }
}
