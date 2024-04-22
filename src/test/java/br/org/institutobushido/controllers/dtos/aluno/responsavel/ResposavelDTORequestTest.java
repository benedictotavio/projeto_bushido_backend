package br.org.institutobushido.controllers.dtos.aluno.responsavel;

import br.org.institutobushido.enums.aluno.FiliacaoResposavel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ResposavelDTORequestTest {
    @Test
    void deveCriarRequisiçaoDeResponsável() {
        String nome = "Nome";
        String cpf = "12345678910";
        String telefone = "11999999999";
        String email = "XHdXr@example.com";
        FiliacaoResposavel filiacao = FiliacaoResposavel.OUTRO;

        ResponsavelDTORequest responsavelDTORequest = new ResponsavelDTORequest(
                nome, cpf, telefone, email, filiacao
        );

        assertEquals(nome, responsavelDTORequest.nome());
        assertEquals(cpf, responsavelDTORequest.cpf());
        assertEquals(telefone, responsavelDTORequest.telefone());
        assertEquals(email, responsavelDTORequest.email());
        assertEquals(filiacao, responsavelDTORequest.filiacao());
    }
}
