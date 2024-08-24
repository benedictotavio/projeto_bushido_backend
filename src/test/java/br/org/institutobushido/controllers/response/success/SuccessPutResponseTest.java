package br.org.institutobushido.controllers.response.success;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class SuccessPutResponseTest {
    @Test
    void deveRetornarSucessoDeUmPut() {
        SuccessPutResponse response = new SuccessPutResponse("123", "Success");
        assertEquals("123", response.getId());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Success", response.getMessage());
        assertNull(response.getEntity());
    }
}
