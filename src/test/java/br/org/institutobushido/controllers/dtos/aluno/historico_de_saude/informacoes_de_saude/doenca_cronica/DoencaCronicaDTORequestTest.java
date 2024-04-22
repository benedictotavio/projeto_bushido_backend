package br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.doenca_cronica;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DoencaCronicaDTORequestTest {

    @Test
    public void doencaCronicaDTORequestTest() {
        String tipo = "Doenca Cronica";

        DoencaCronicaDTORequest doencaCronicaDTORequest = new DoencaCronicaDTORequest(tipo);

        assertEquals(tipo, doencaCronicaDTORequest.tipo());
    }
}
