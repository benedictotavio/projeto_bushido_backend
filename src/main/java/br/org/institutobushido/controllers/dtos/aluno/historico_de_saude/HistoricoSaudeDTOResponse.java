package br.org.institutobushido.controllers.dtos.aluno.historico_de_saude;

import java.util.List;

import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.alergia.AlergiaDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.cirurgia.CirurgiaDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.doenca_cronica.DoencaCronicaDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.uso_medicamento_continuo.UsoMedicamentoContinuoDTOResponse;
import br.org.institutobushido.enums.aluno.FatorRH;
import br.org.institutobushido.enums.aluno.TipoSanguineo;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record HistoricoSaudeDTOResponse(FatorRH fatorRh, TipoSanguineo tipoSanguineo,
                UsoMedicamentoContinuoDTOResponse usoMedicamento, AlergiaDTOResponse alergia,
                CirurgiaDTOResponse cirurgia, DoencaCronicaDTOResponse doencaCronica, List<String> deficiencias,
                List<String> acompanhamentoSaude) {
}