package br.org.institutobushido.controllers.response.success;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SuccessLoginAuthenticatedTest {
    @Test
    public void test_initialize_success_login_authenticated_with_fields() {
        String token = "token";
        String role = "role";
        SuccessLoginAuthenticated successLoginAuthenticated = new SuccessLoginAuthenticated(token, role);

        assertEquals(token, successLoginAuthenticated.getToken());
        assertEquals(role, successLoginAuthenticated.getRole());
        assertEquals(HttpStatus.OK.value(), successLoginAuthenticated.getStatus());
        assertTrue(successLoginAuthenticated.isSuccess());
    }

    @Test
    public void test_initialize_success_login_authenticated_with_null_values() {
        String token = null;
        String role = null;
        SuccessLoginAuthenticated successLoginAuthenticated = new SuccessLoginAuthenticated(token, role);

        assertNull(successLoginAuthenticated.getToken());
        assertNull(successLoginAuthenticated.getRole());
        assertEquals(HttpStatus.OK.value(), successLoginAuthenticated.getStatus());
        assertTrue(successLoginAuthenticated.isSuccess());
    }
}
