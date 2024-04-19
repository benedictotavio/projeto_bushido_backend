package br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.doenca_cronica;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DoencaCronicaDTOResponseTest {

    @Test
    public void doencaCronicaDTOResponseTest() {
        String doencaCronica = "DoencaCronica";
        boolean resposta = true;

        DoencaCronicaDTOResponse doencaCronicaDTOResponse = new DoencaCronicaDTOResponse(resposta,doencaCronica);

        assert(doencaCronicaDTOResponse.resposta() == resposta);
        assert(doencaCronicaDTOResponse.tipo().equals(doencaCronica));
    }
}
