package br.org.institutobushido.controllers.response.success;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SuccessDeleteResponseTest {
    @Test
    public void test_createObjectWithIdAndMessage() {
        String id = "123";
        String message = "Success";

        SuccessDeleteResponse response = new SuccessDeleteResponse(id, message);

        assertEquals(id, response.getId());
        assertEquals(message, response.getMessage());
        assertTrue(response.isSuccess());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
