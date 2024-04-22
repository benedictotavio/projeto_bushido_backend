package br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.cirurgia;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CirurgiaDTORequestTest {

    @Test
    public void cirurgiaDTORequestTest() {
        String tipo = "Cirurgia";

        CirurgiaDTORequest cirurgiaDTORequest = new CirurgiaDTORequest(tipo);

        assertEquals(tipo, cirurgiaDTORequest.tipo());
    }
}
