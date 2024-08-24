package br.org.institutobushido.providers.mappers.aluno;

import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.HistoricoSaudeDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.HistoricoSaudeDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.alergia.AlergiaDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.cirurgia.CirurgiaDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.doenca_cronica.DoencaCronicaDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.uso_medicamento_continuo.UsoMedicamentoContinuoDTOResponse;
import br.org.institutobushido.models.aluno.historico_de_saude.HistoricoSaude;
import br.org.institutobushido.models.aluno.historico_de_saude.informacoes_saude.Alergia;
import br.org.institutobushido.models.aluno.historico_de_saude.informacoes_saude.Cirurgia;
import br.org.institutobushido.models.aluno.historico_de_saude.informacoes_saude.DoencaCronica;
import br.org.institutobushido.models.aluno.historico_de_saude.informacoes_saude.UsoMedicamentoContinuo;

public class HistoricoSaudeMapper {

        private HistoricoSaudeMapper() {
        }

        public static HistoricoSaude mapToHistoricoSaude(HistoricoSaudeDTORequest historicoSaudeDTORequest) {
                if (historicoSaudeDTORequest == null) {
                        return null;
                }

                return new HistoricoSaude(
                                historicoSaudeDTORequest.tipoSanguineo(),
                                new UsoMedicamentoContinuo(
                                                historicoSaudeDTORequest.usoMedicamentoContinuo().tipo()),
                                new DoencaCronica(
                                                historicoSaudeDTORequest.doencaCronica().tipo()),
                                new Alergia(
                                                historicoSaudeDTORequest.alergia().tipo()),
                                new Cirurgia(
                                                historicoSaudeDTORequest.cirurgia().tipo()),
                                historicoSaudeDTORequest.deficiencia(),
                                historicoSaudeDTORequest.acompanhamentoSaude());
        }

        public static HistoricoSaude mapToHistoricoSaude(HistoricoSaudeDTOResponse historicoSaudeDTOResponse) {
                if (historicoSaudeDTOResponse == null) {
                        return null;
                }

                return new HistoricoSaude(
                                historicoSaudeDTOResponse.tipoSanguineo(),
                                new UsoMedicamentoContinuo(
                                                historicoSaudeDTOResponse.usoMedicamentoContinuo().tipo()),
                                new DoencaCronica(
                                                historicoSaudeDTOResponse.doencaCronica().tipo()),
                                new Alergia(
                                                historicoSaudeDTOResponse.alergia().tipo()),
                                new Cirurgia(
                                                historicoSaudeDTOResponse.cirurgia().tipo()),
                                historicoSaudeDTOResponse.deficiencias(),
                                historicoSaudeDTOResponse.acompanhamentoSaude());
        }

        public static HistoricoSaudeDTOResponse mapToHistoricoSaudeDTOResponse(HistoricoSaude historicoSaude) {
                if (historicoSaude == null) {
                        return null;
                }
                return HistoricoSaudeDTOResponse.builder()
                                .withTipoSanguineo(historicoSaude.getTipoSanguineo())
                                .withAlergia(AlergiaDTOResponse.builder()
                                                .withResposta(historicoSaude.getAlergia().getResposta())
                                                .withTipo(historicoSaude.getAlergia().getTipo()).build())
                                .withCirurgia(CirurgiaDTOResponse.builder()
                                                .withResposta(historicoSaude.getCirurgia().getResposta())
                                                .withTipo(historicoSaude.getCirurgia().getTipo()).build())
                                .withDoencaCronica(
                                                DoencaCronicaDTOResponse.builder()
                                                                .withResposta(historicoSaude.getDoencaCronica()
                                                                                .getResposta())
                                                                .withTipo(historicoSaude.getDoencaCronica().getTipo())
                                                                .build())
                                .withUsoMedicamentoContinuo(UsoMedicamentoContinuoDTOResponse.builder()
                                                .withResposta(historicoSaude.getUsoMedicamentoContinuo().getResposta())
                                                .withTipo(historicoSaude.getUsoMedicamentoContinuo().getTipo()).build())
                                .withDeficiencias(historicoSaude.getDeficiencias())
                                .withAcompanhamentoSaude(historicoSaude.getAcompanhamentoSaude())
                                .build();
        }
}
