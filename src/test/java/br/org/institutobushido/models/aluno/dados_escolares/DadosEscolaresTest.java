package br.org.institutobushido.models.aluno.dados_escolares;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class DadosEscolaresTest {

    private DadosEscolares dadosEscolares;

    @BeforeEach
    void setUp() {
        dadosEscolares = new DadosEscolares(
                "ESCOLA"
        );
    }

    @Test
    void deveInstanciarClasse() {
        dadosEscolares.setEscola("ESCOLA1");
        assertEquals("ESCOLA1", dadosEscolares.getEscola());
    }

    @Test
    void deveManterOsMesmosValoresSeEstiverVazio() {
        dadosEscolares = new DadosEscolares();
        dadosEscolares.setEscola("");
        assertNull(dadosEscolares.getEscola());
    }
}
