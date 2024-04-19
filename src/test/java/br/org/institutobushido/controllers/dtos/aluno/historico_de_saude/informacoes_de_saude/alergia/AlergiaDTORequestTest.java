package br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.alergia;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AlergiaDTORequestTest {

    @Test
    void alergiaDTORequest() {
        String tipo = "tipo";
        AlergiaDTORequest alergiaDTORequest = new AlergiaDTORequest(tipo);

        assert(alergiaDTORequest.tipo().equals(tipo));
    }
}
