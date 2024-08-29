package br.org.institutobushido.controllers.response.error;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StandardErrorTest {

    private StandardError standardError;

    @BeforeEach
    void setUp() {
        standardError = new StandardError();

    }
    @Test
    void testarParametrosValidos() {
        Instant timestamp = Instant.now();
        Integer status = 200;
        String error = "OK";
        String message = "Success";
        String path = "/api";

        standardError.setTimestamp(timestamp);
        standardError.setStatus(status);
        standardError.setError(error);
        standardError.setMessage(message);
        standardError.setPath(path);

        assertEquals(timestamp, standardError.getTimestamp());
        assertEquals(status, standardError.getStatus());
        assertEquals(error, standardError.getError());
        assertEquals(message, standardError.getMessage());
        assertEquals(path, standardError.getPath());
    }
}
