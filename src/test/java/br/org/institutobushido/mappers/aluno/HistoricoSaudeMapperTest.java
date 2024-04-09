package br.org.institutobushido.mappers.aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.HistoricoSaudeDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.HistoricoSaudeDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.alergia.AlergiaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.alergia.AlergiaDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.cirurgia.CirurgiaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.cirurgia.CirurgiaDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.doenca_cronica.DoencaCronicaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.doenca_cronica.DoencaCronicaDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.uso_medicamento_continuo.UsoMedicamentoContinuoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.uso_medicamento_continuo.UsoMedicamentoContinuoDTOResponse;
import br.org.institutobushido.enums.aluno.TipoSanguineo;
import br.org.institutobushido.models.aluno.historico_de_saude.HistoricoSaude;
import br.org.institutobushido.models.aluno.historico_de_saude.informacoes_saude.Alergia;
import br.org.institutobushido.models.aluno.historico_de_saude.informacoes_saude.Cirurgia;
import br.org.institutobushido.models.aluno.historico_de_saude.informacoes_saude.DoencaCronica;
import br.org.institutobushido.models.aluno.historico_de_saude.informacoes_saude.UsoMedicamentoContinuo;

@SpringBootTest
class HistoricoSaudeMapperTest {
    private HistoricoSaude historicoSaude;
    private HistoricoSaudeDTORequest historicoSaudeDTORequest;
    private HistoricoSaudeDTOResponse historicoSaudeDTOResponse;

    @BeforeEach
    void setUp() {
        historicoSaude = new HistoricoSaude(
                TipoSanguineo.AB_NEGATIVO,
                new UsoMedicamentoContinuo( "usoMedicamentoContinuo"),
                new DoencaCronica( "doencaCronica"),
                new Alergia( "alergia"),
                new Cirurgia( "cirurgia"),
                List.of("deficiencia"),
                List.of("acompanhamentoSaude")
        );
        historicoSaudeDTORequest = new HistoricoSaudeDTORequest(
                TipoSanguineo.AB_NEGATIVO,
                new UsoMedicamentoContinuoDTORequest("tipo"),
                new AlergiaDTORequest("alergia"),
                new CirurgiaDTORequest("cirurgia"),
                new DoencaCronicaDTORequest("doencaCronica"),
                List.of("deficiencia"),
                List.of("acompanhamentoSaude"));
        historicoSaudeDTOResponse = new HistoricoSaudeDTOResponse(
                TipoSanguineo.AB_NEGATIVO,
                new UsoMedicamentoContinuoDTOResponse(true, "tipo"),
                new AlergiaDTOResponse(true, "alergia"),
                new CirurgiaDTOResponse(true, "cirurgia"),
                new DoencaCronicaDTOResponse(true, "doencaCronica"),
                List.of("deficiencia"),
                List.of("acompanhamentoSaude"));
    }

    @Test
    void deveMapearHistoricoSaudeDeHistoricoSaudeDTORequest() {
        historicoSaude = HistoricoSaudeMapper.mapToHistoricoSaude(historicoSaudeDTORequest);
        assertEquals(historicoSaude.getTipoSanguineo(), historicoSaudeDTORequest.tipoSanguineo());
        assertEquals(historicoSaude.getAlergia().getTipo(), historicoSaudeDTORequest.alergia().tipo());
        assertEquals(historicoSaude.getCirurgia().getTipo(), historicoSaudeDTORequest.cirurgia().tipo());
        assertEquals(historicoSaude.getDoencaCronica().getTipo(), historicoSaudeDTORequest.doencaCronica().tipo());
        assertEquals(historicoSaude.getUsoMedicamentoContinuo().getTipo(), historicoSaudeDTORequest.usoMedicamentoContinuo().tipo());
        assertEquals(historicoSaude.getDeficiencias().size(), historicoSaudeDTORequest.deficiencia().size());
        assertEquals(historicoSaude.getAcompanhamentoSaude(), historicoSaudeDTORequest.acompanhamentoSaude());
    }

    @Test
    void deveMapearHistoricoDTOResonseDeHistoricoSaude() {
        historicoSaudeDTOResponse = HistoricoSaudeMapper.mapToHistoricoSaudeDTOResponse(historicoSaude);
        assertEquals(historicoSaudeDTOResponse.tipoSanguineo(), historicoSaude.getTipoSanguineo());
        assertEquals(historicoSaudeDTOResponse.alergia().tipo(), historicoSaude.getAlergia().getTipo());
        assertEquals(historicoSaudeDTOResponse.cirurgia().tipo(), historicoSaude.getCirurgia().getTipo());
        assertEquals(historicoSaudeDTOResponse.doencaCronica().tipo(), historicoSaude.getDoencaCronica().getTipo());
        assertEquals(historicoSaudeDTOResponse.usoMedicamentoContinuo().tipo(), historicoSaude.getUsoMedicamentoContinuo().getTipo());
        assertEquals(historicoSaudeDTOResponse.deficiencias().size(), historicoSaude.getDeficiencias().size());
        assertEquals(historicoSaudeDTOResponse.acompanhamentoSaude(), historicoSaude.getAcompanhamentoSaude());
    }

}
