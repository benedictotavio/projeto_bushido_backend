package br.org.institutobushido.controllers.response.error;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StandardErrorTest {
    @Test
    public void test_valid_parameters() {
        Instant timestamp = Instant.now();
        Integer status = 200;
        String error = "OK";
        String message = "Success";
        String path = "/api";

        StandardError standardError = new StandardError(timestamp, status, error, message, path);

        assertEquals(timestamp, standardError.getTimestamp());
        assertEquals(status, standardError.getStatus());
        assertEquals(error, standardError.getError());
        assertEquals(message, standardError.getMessage());
        assertEquals(path, standardError.getPath());
    }
}
