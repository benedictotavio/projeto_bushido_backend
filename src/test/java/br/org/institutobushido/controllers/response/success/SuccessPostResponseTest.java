package br.org.institutobushido.controllers.response.success;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SuccessPostResponseTest {
    @Test
    public void test_instantiation_with_message_and_id() {
        // Arrange
        String id = "123";
        String message = "Test message";

        // Act
        SuccessPostResponse response = new SuccessPostResponse(id, message);

        // Assert
        assertNotNull(response);
        assertEquals(id, response.getId());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(message, response.getMessage());
    }
}
