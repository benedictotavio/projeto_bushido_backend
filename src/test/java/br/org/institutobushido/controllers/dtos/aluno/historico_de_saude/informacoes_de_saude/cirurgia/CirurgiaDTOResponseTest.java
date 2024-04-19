package br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.cirurgia;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CirurgiaDTOResponseTest {
    @Test
    void cirurgiaDTOResponseTest() {
        String tipo = "Cirurgia";
        boolean resposta = true;

        CirurgiaDTOResponse cirurgiaDTOResponse = new CirurgiaDTOResponse(resposta,tipo);
        assert(cirurgiaDTOResponse.resposta() == resposta);
        assert(cirurgiaDTOResponse.tipo() == tipo);
    }
}
