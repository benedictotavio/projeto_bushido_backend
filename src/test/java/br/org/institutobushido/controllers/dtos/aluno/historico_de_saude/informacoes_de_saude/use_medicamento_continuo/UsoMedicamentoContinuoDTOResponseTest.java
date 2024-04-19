package br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.use_medicamento_continuo;

import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.uso_medicamento_continuo.UsoMedicamentoContinuoDTOResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsoMedicamentoContinuoDTOResponseTest {

    @Test
    void deveRetornarUsoMedicamento(){
        String doencaCronica = "DoencaCronica";
        boolean resposta = true;

        UsoMedicamentoContinuoDTOResponse usoMedicamentoContinuoDTOResponse = new UsoMedicamentoContinuoDTOResponse(resposta,doencaCronica);

        assert(usoMedicamentoContinuoDTOResponse.resposta() == resposta);
        assert(usoMedicamentoContinuoDTOResponse.tipo().equals(doencaCronica));
    }
}
