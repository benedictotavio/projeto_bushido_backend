package br.org.institutobushido.controllers.dtos.aluno.historico_de_saude;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.alergia.AlergiaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.cirurgia.CirurgiaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.doenca_cronica.DoencaCronicaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.uso_medicamento_continuo.UsoMedicamentoContinuoDTORequest;
import br.org.institutobushido.enums.aluno.TipoSanguineo;

@SpringBootTest
class UpdateHistoricoSaudeDTORequestTest {

    @Test
    void test_createInstanceWithAllParameters() {
        // Arrange
        TipoSanguineo tipoSanguineo = TipoSanguineo.AB_POSITIVO;
        UsoMedicamentoContinuoDTORequest usoMedicamentoContinuo = new UsoMedicamentoContinuoDTORequest("Medicamento");
        AlergiaDTORequest alergia = new AlergiaDTORequest("Alergia");
        CirurgiaDTORequest cirurgia = new CirurgiaDTORequest("Cirurgia");
        DoencaCronicaDTORequest doencaCronica = new DoencaCronicaDTORequest("Doença");

        // Act
        UpdateHistoricoSaudeDTORequest updateHistoricoSaudeDTORequest = new UpdateHistoricoSaudeDTORequest(
                tipoSanguineo, usoMedicamentoContinuo, alergia, cirurgia, doencaCronica);

        // Assert
        assertNotNull(updateHistoricoSaudeDTORequest);
        assertEquals(tipoSanguineo, updateHistoricoSaudeDTORequest.tipoSanguineo());
        assertEquals(usoMedicamentoContinuo, updateHistoricoSaudeDTORequest.usoMedicamentoContinuo());
        assertEquals(alergia, updateHistoricoSaudeDTORequest.alergia());
        assertEquals(cirurgia, updateHistoricoSaudeDTORequest.cirurgia());
        assertEquals(doencaCronica, updateHistoricoSaudeDTORequest.doencaCronica());
    }

    @Test
    void test_createInstanceWithNullTipoSanguineo() {
        // Arrange
        TipoSanguineo tipoSanguineo = null;
        UsoMedicamentoContinuoDTORequest usoMedicamentoContinuo = new UsoMedicamentoContinuoDTORequest("Medicamento");
        AlergiaDTORequest alergia = new AlergiaDTORequest("Alergia");
        CirurgiaDTORequest cirurgia = new CirurgiaDTORequest("Cirurgia");
        DoencaCronicaDTORequest doencaCronica = new DoencaCronicaDTORequest("Doença");

        // Act
        UpdateHistoricoSaudeDTORequest updateHistoricoSaudeDTORequest = new UpdateHistoricoSaudeDTORequest(
                tipoSanguineo, usoMedicamentoContinuo, alergia, cirurgia, doencaCronica);

        // Assert
        assertNotNull(updateHistoricoSaudeDTORequest);
        assertNull(updateHistoricoSaudeDTORequest.tipoSanguineo());
        assertEquals(usoMedicamentoContinuo, updateHistoricoSaudeDTORequest.usoMedicamentoContinuo());
        assertEquals(alergia, updateHistoricoSaudeDTORequest.alergia());
        assertEquals(cirurgia, updateHistoricoSaudeDTORequest.cirurgia());
        assertEquals(doencaCronica, updateHistoricoSaudeDTORequest.doencaCronica());
    }
}
