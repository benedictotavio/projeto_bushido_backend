package br.org.institutobushido.controllers.dtos.aluno.historico_de_saude;

import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.alergia.AlergiaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.cirurgia.CirurgiaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.doenca_cronica.DoencaCronicaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.uso_medicamento_continuo.UsoMedicamentoContinuoDTORequest;
import br.org.institutobushido.enums.aluno.TipoSanguineo;

public record UpdateHistoricoSaudeDTORequest(
        TipoSanguineo tipoSanguineo,
        UsoMedicamentoContinuoDTORequest usoMedicamentoContinuo,
        AlergiaDTORequest alergia,
        CirurgiaDTORequest cirurgia,
        DoencaCronicaDTORequest doencaCronica) {

    public UpdateHistoricoSaudeDTORequest {

        if (usoMedicamentoContinuo == null || usoMedicamentoContinuo.tipo() == null) {
            usoMedicamentoContinuo = new UsoMedicamentoContinuoDTORequest("");
        }
        if (alergia == null || alergia.tipo() == null) {
            alergia = new AlergiaDTORequest("");
        }
        if (cirurgia == null || cirurgia.tipo() == null) {
            cirurgia = new CirurgiaDTORequest("");
        }
        if (doencaCronica == null || doencaCronica.tipo() == null) {
            doencaCronica = new DoencaCronicaDTORequest("");
        }
    }
}
