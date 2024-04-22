package br.org.institutobushido.controllers.dtos.aluno.graduacao;

import br.org.institutobushido.controllers.dtos.aluno.graduacao.faltas.FaltaDTOResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GraduacaoDTOResponseTest {

    @Test
    public void deveRetornarGraduacaoDoAluno() {

        int kyu = 6;
        int dan = 2;
        List<FaltaDTOResponse> faltas = List.of(
                new FaltaDTOResponse("Motivo", "Observação", "Data")
        );
        boolean status = true;
        LocalDate inicioGraduacao = LocalDate.now();
        LocalDate fimGraduacao = LocalDate.now();
        int frequencia = 100;
        boolean aprovado = true;
        int cargaHoraria = 100;

        GraduacaoDTOResponse request = new GraduacaoDTOResponse(
                kyu, faltas, status, inicioGraduacao, fimGraduacao, frequencia, aprovado, cargaHoraria, dan
        );
        assertEquals(kyu, request.kyu());
        assertEquals(faltas, request.faltas());
        assertEquals(status, request.status());
        assertEquals(inicioGraduacao, request.inicioGraduacao());
        assertEquals(fimGraduacao, request.fimGraduacao());
        assertEquals(frequencia, request.frequencia());
        assertEquals(aprovado, request.aprovado());
        assertEquals(cargaHoraria, request.cargaHoraria());
        assertEquals(dan, request.dan());
    }
}
