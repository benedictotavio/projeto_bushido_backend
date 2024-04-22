package br.org.institutobushido.controllers.dtos.aluno.graduacao;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GraduacaoDTORequestTest {

    @Test
    public void deveRetornarGraduacaoDaRequisicao() {
        int kyu = 6;
        int dan = 2;
        GraduacaoDTORequest request = new GraduacaoDTORequest(kyu, dan);

        assertEquals(kyu, request.kyu());
        assertEquals(dan, request.dan());
    }
}
