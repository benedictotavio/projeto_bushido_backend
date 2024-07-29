package br.org.institutobushido.controllers.response.success;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SuccessDeleteResponseTest {
    @Test
    void deveCriarSuccessDeleteResponse() {
        String id = "123";
        String message = "Success";

        SuccessDeleteResponse response = new SuccessDeleteResponse(id, message);

        assertEquals(id, response.getId());
        assertEquals(message, response.getMessage());
        assertTrue(response.isSuccess());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
