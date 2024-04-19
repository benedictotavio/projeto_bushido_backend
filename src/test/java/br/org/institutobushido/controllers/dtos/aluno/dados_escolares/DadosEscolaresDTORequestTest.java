package br.org.institutobushido.controllers.dtos.aluno.dados_escolares;

import br.org.institutobushido.enums.aluno.Turno;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DadosEscolaresDTORequestTest {
    @Test
    void test_valid_instance_with_all_required_fields() {
        DadosEscolaresDTORequest dadosEscolaresDTORequest = DadosEscolaresDTORequest.builder()
                .withTurno(Turno.MANHA)
                .withEscola("escola")
                .withSerie("serie")
                .build();

        assertNotNull(dadosEscolaresDTORequest);
        assertEquals(Turno.MANHA, dadosEscolaresDTORequest.turno());
        assertEquals("escola", dadosEscolaresDTORequest.escola());
        assertEquals("serie", dadosEscolaresDTORequest.serie());
    }
}
