package br.org.institutobushido.controllers.dtos.aluno.historico_de_saude;

import java.util.List;

import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.alergia.AlergiaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.cirurgia.CirurgiaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.doenca_cronica.DoencaCronicaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.uso_medicamento_continuo.UsoMedicamentoContinuoDTORequest;
import br.org.institutobushido.providers.enums.aluno.TipoSanguineo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record HistoricoSaudeDTORequest(
        @NotNull(message = "Tipo Sanguineo é obrigatório") @Pattern(regexp = "^(B_POSITIVO, A_POSITIVO, O_POSITIVO, B_NEGATIVO, A_NEGATIVO, O_NEGATIVO, AB_POSITIVO, AB_NEGATIVO)$", message = "Tipo Sanguineo inválido!") TipoSanguineo tipoSanguineo,
        UsoMedicamentoContinuoDTORequest usoMedicamentoContinuo,
        AlergiaDTORequest alergia,
        CirurgiaDTORequest cirurgia,
        DoencaCronicaDTORequest doencaCronica,
        List<String> deficiencia,
        List<String> acompanhamentoSaude) {

    public HistoricoSaudeDTORequest {
        if (usoMedicamentoContinuo == null) {
            usoMedicamentoContinuo = new UsoMedicamentoContinuoDTORequest("");
        }
        if (alergia == null) {
            alergia = new AlergiaDTORequest("");
        }
        if (cirurgia == null) {
            cirurgia = new CirurgiaDTORequest("");
        }
        if (doencaCronica == null) {
            doencaCronica = new DoencaCronicaDTORequest("");
        }
        if (deficiencia == null) {
            deficiencia = List.of();
        }
        if (acompanhamentoSaude == null) {
            acompanhamentoSaude = List.of();
        }
    }
}