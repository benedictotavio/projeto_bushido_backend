package br.org.institutobushido.controllers.dtos.aluno.graduacao.faltas;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FaltaDTORequestTest {

    @Test
    public void deveRetornarFaltaDaRequisicao() {
        String motivo = "Motivo";
        String observacao = "Observação";

        FaltaDTORequest request = new FaltaDTORequest(
                motivo, observacao
        );

        assertEquals(motivo, request.motivo());
        assertEquals(observacao, request.observacao());

    };
}
