package br.org.institutobushido.controllers.dtos.aluno.dados_escolares;

import br.org.institutobushido.enums.aluno.Turno;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UpdateDadosEscolaresDTORequestTest {

    @Test
    public void deveRetornarTurnoESerieESerieEscolar() {
        Turno turno = Turno.MANHA;
        String escola = "escola";
        String serie = "serie";
        UpdateDadosEscolaresDTORequest updateDadosEscolaresDTORequest = new UpdateDadosEscolaresDTORequest(turno, escola, serie);
        assertEquals(turno, updateDadosEscolaresDTORequest.turno());
        assertEquals(escola, updateDadosEscolaresDTORequest.escola());
        assertEquals(serie, updateDadosEscolaresDTORequest.serie());
    }
}
