package br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.alergia;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AlergiaDTOResponseTest {

    @Test
    void deveRetornarAlergiaDTOResponseTest() {
        String tipo = "tipo";
        boolean resposta = true;
        AlergiaDTOResponse alergiaDTOResponse = new AlergiaDTOResponse(resposta,tipo);

        assert(alergiaDTOResponse.resposta() == resposta);
        assert(alergiaDTOResponse.tipo() == tipo);
    }
}
