package br.org.institutobushido.controllers.dtos.aluno.endereco;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EnderecoDTORequestTest {
    @Test
    public void deveRetornarEnderecoDaRequisicao() {
        EnderecoDTORequest enderecoDTORequest = new EnderecoDTORequest(
                "CIDADE", "ESTADO", "12345678", "NUMERO");

        assertEquals("CIDADE", enderecoDTORequest.cidade());
        assertEquals("ESTADO", enderecoDTORequest.estado());
        assertEquals("12345678", enderecoDTORequest.cep());
        assertEquals("NUMERO", enderecoDTORequest.numero());
    }
}
