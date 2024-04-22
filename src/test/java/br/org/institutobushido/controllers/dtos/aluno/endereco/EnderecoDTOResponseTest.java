package br.org.institutobushido.controllers.dtos.aluno.endereco;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EnderecoDTOResponseTest {
    @Test
    public void deveRetornarEnderecoDaResposta() {
        String cidade = "Cidade";
        String estado = "Estado";
        String cep = "12345678";
        String numero = "123";
        EnderecoDTOResponse response = new EnderecoDTOResponse(
                cidade, estado, cep, numero
        );

        assertEquals(cidade, response.cidade());
        assertEquals(estado, response.estado());
        assertEquals(cep, response.cep());
        assertEquals(numero, response.numero());
    }
}
