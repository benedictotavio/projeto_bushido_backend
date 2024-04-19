package br.org.institutobushido.controllers.dtos.aluno.responsavel;

import br.org.institutobushido.enums.aluno.FiliacaoResposavel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ResposavelDTOResponseTest {
    @Test
    void deveCriarRequisiçaoDeResponsável() {
        String nome = "Nome";
        String cpf = "12345678910";
        String telefone = "11999999999";
        String email = "XHdXr@example.com";
        String filiacao = FiliacaoResposavel.OUTRO.name();

        ResponsavelDTOResponse responsavelDTOResponse = new ResponsavelDTOResponse(
                nome, cpf, telefone, email, filiacao
        );

        assertEquals(nome, responsavelDTOResponse.nome());
        assertEquals(cpf, responsavelDTOResponse.cpf());
        assertEquals(telefone, responsavelDTOResponse.telefone());
        assertEquals(email, responsavelDTOResponse.email());
        assertEquals(filiacao, responsavelDTOResponse.filiacao());
    }
}
