package br.org.institutobushido.controllers.dtos.aluno.dados_escolares;

import br.org.institutobushido.enums.aluno.Turno;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DadosEscolaresDTOResponseTest {

    @Test
    void deveRetornarTurnoESerieESerieEscolar() {
        Turno turno = Turno.MANHA;
        String escola = "escola";
        String serie = "serie";
        DadosEscolaresDTOResponse dadosEscolaresDTOResponse = new DadosEscolaresDTOResponse(turno, escola, serie);
        assertEquals(turno, dadosEscolaresDTOResponse.turno());
        assertEquals(escola, dadosEscolaresDTOResponse.escola());
        assertEquals(serie, dadosEscolaresDTOResponse.serie());
    }
}
