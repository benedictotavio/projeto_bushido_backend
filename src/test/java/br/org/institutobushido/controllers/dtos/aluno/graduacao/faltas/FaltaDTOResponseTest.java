package br.org.institutobushido.controllers.dtos.aluno.graduacao.faltas;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FaltaDTOResponseTest {
    @Test
    void deveRetornarFaltaDoAluno() {
        String motivo = "Motivo";
        String observacao = "Observacao";
        String data = new Date().toString();
        FaltaDTOResponse faltaDTOResponse = new FaltaDTOResponse(motivo, observacao, data);
        assertEquals(motivo, faltaDTOResponse.motivo());
        assertEquals(observacao, faltaDTOResponse.observacao());
        assertEquals(data, faltaDTOResponse.data());
    }
}
