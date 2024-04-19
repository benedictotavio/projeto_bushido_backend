package br.org.institutobushido.controllers.dtos.aluno.historico_de_saude;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.alergia.AlergiaDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.cirurgia.CirurgiaDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.doenca_cronica.DoencaCronicaDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.uso_medicamento_continuo.UsoMedicamentoContinuoDTOResponse;
import br.org.institutobushido.enums.aluno.TipoSanguineo;

@SpringBootTest
class HistoricoSaudeDTOResponseTest {
    @Test
    void deveConstruirRespostaDeHistorioSaude() {
        HistoricoSaudeDTOResponse historicoSaudeDTOResponse = new HistoricoSaudeDTOResponse(
                TipoSanguineo.AB_NEGATIVO,
                new UsoMedicamentoContinuoDTOResponse(true, "tipo"),
                new AlergiaDTOResponse(true, "alergia"),
                new CirurgiaDTOResponse(true, "cirurgia"),
                new DoencaCronicaDTOResponse(true, "doencaCronica"),
                List.of("deficiencia"),
                List.of("acompanhamentoSaude")
        );

        assertEquals(TipoSanguineo.AB_NEGATIVO, historicoSaudeDTOResponse.tipoSanguineo());
        assertEquals(true, historicoSaudeDTOResponse.usoMedicamentoContinuo().resposta());
        assertEquals("tipo", historicoSaudeDTOResponse.usoMedicamentoContinuo().tipo());
        assertEquals(true, historicoSaudeDTOResponse.alergia().resposta());
        assertEquals("alergia", historicoSaudeDTOResponse.alergia().tipo());
        assertEquals(true, historicoSaudeDTOResponse.cirurgia().resposta());
        assertEquals("cirurgia", historicoSaudeDTOResponse.cirurgia().tipo());
        assertEquals(true, historicoSaudeDTOResponse.doencaCronica().resposta());
        assertEquals("doencaCronica", historicoSaudeDTOResponse.doencaCronica().tipo());
        assertEquals(List.of("deficiencia"), historicoSaudeDTOResponse.deficiencias());
        assertEquals(List.of("acompanhamentoSaude"), historicoSaudeDTOResponse.acompanhamentoSaude());
    }
}
