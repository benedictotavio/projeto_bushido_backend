package br.org.institutobushido.controllers.dtos.aluno.endereco;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UpdateEnderecoDTORequestTest {
    @Test
    public void test_createInstanceWithValidParameters() {
        // Arrange
        String cidade = "CIDADE";
        String estado = "ESTADO";
        String cep = "CEP";
        String numero = "NUMERO";

        // Act
        UpdateEnderecoDTORequest updateEnderecoDTORequest = new UpdateEnderecoDTORequest(cidade, estado, cep, numero);

        // Assert
        assertNotNull(updateEnderecoDTORequest);
        assertEquals(cidade, updateEnderecoDTORequest.cidade());
        assertEquals(estado, updateEnderecoDTORequest.estado());
        assertEquals(cep, updateEnderecoDTORequest.cep());
        assertEquals(numero, updateEnderecoDTORequest.numero());
    }
}
