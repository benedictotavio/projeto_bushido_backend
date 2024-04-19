package br.org.institutobushido.controllers.dtos.aluno.historico_de_saude;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.alergia.AlergiaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.cirurgia.CirurgiaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.doenca_cronica.DoencaCronicaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.uso_medicamento_continuo.UsoMedicamentoContinuoDTORequest;
import br.org.institutobushido.enums.aluno.TipoSanguineo;

@SpringBootTest
class HistoricoSaudeDTORequestTest {
    @Test
    void deveRetornarHistoricoSaude() {
        String alergia = "alergia";
        String doencaCronica = "doenca cronica";
        String cirurgia = "cirurgia";
        String usoMedicamento = "uso medicamento";

        HistoricoSaudeDTORequest request = new HistoricoSaudeDTORequest(
                TipoSanguineo.A_NEGATIVO,
                new UsoMedicamentoContinuoDTORequest(usoMedicamento),
                new AlergiaDTORequest(alergia),
                new CirurgiaDTORequest(cirurgia),
                new DoencaCronicaDTORequest(doencaCronica),
                List.of(
                        "deficiencia"),
                List.of(
                        "test"));

        assertEquals(request.alergia().tipo(), alergia);
        assertEquals(request.doencaCronica().tipo(), doencaCronica);
        assertEquals(request.cirurgia().tipo(), cirurgia);
        assertEquals(request.usoMedicamentoContinuo().tipo(), usoMedicamento);
    }

}
