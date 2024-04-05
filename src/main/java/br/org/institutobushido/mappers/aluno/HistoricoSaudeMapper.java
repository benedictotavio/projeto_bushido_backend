package br.org.institutobushido.mappers.aluno;

import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.HistoricoSaudeDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.HistoricoSaudeDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.alergia.AlergiaDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.cirurgia.CirurgiaDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.doenca_cronica.DoencaCronicaDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.uso_medicamento_continuo.UsoMedicamentoContinuoDTOResponse;
import br.org.institutobushido.model.aluno.historico_de_saude.HistoricoSaude;
import br.org.institutobushido.model.aluno.historico_de_saude.informacoes_saude.Alergia;
import br.org.institutobushido.model.aluno.historico_de_saude.informacoes_saude.Cirurgia;
import br.org.institutobushido.model.aluno.historico_de_saude.informacoes_saude.DoencaCronica;
import br.org.institutobushido.model.aluno.historico_de_saude.informacoes_saude.UsoMedicamentoContinuo;

public class HistoricoSaudeMapper {

        private HistoricoSaudeMapper() {
        }

        public static HistoricoSaude mapToHistoricoSaude(HistoricoSaudeDTORequest historicoSaudeDTORequest) {
                if (historicoSaudeDTORequest == null) {
                        return null;
                }

                HistoricoSaude historicoSaude = new HistoricoSaude();

                historicoSaude.setTipoSanguineo(historicoSaudeDTORequest.tipoSanguineo());
                historicoSaude.setFatorRh(historicoSaudeDTORequest.fatorRh());
                historicoSaude.setAlergia(
                                new Alergia(historicoSaudeDTORequest.alergia().resposta(),
                                                historicoSaudeDTORequest.alergia().tipo()));
                historicoSaude.setCirurgia(new Cirurgia(historicoSaudeDTORequest.cirurgia().resposta(),
                                historicoSaudeDTORequest.cirurgia().tipo()));
                historicoSaude.setDoencaCronica(new DoencaCronica(historicoSaudeDTORequest.doencaCronica().resposta(),
                                historicoSaudeDTORequest.doencaCronica().tipo()));
                historicoSaude.setUsoMedicamentoContinuo(new UsoMedicamentoContinuo(
                                historicoSaudeDTORequest.usoMedicamentoContinuo().resposta(),
                                historicoSaudeDTORequest.usoMedicamentoContinuo().tipo()));
                historicoSaude.setDeficiencias(historicoSaudeDTORequest.deficiencia());
                historicoSaude.setAcompanhamentoSaude(historicoSaudeDTORequest.acompanhamentoSaude());
                return historicoSaude;
        }

        public static HistoricoSaudeDTOResponse mapToHistoricoSaudeDTOResponse(HistoricoSaude historicoSaude) {
                if (historicoSaude == null) {
                        return null;
                }
                return HistoricoSaudeDTOResponse.builder()
                                .withFatorRh(historicoSaude.getFatorRh())
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
                                .withUsoMedicamento(UsoMedicamentoContinuoDTOResponse.builder()
                                                .withResposta(historicoSaude.getUsoMedicamentoContinuo().getResposta())
                                                .withTipo(historicoSaude.getUsoMedicamentoContinuo().getTipo()).build())
                                .withDeficiencias(historicoSaude.getDeficiencias())
                                .withAcompanhamentoSaude(historicoSaude.getAcompanhamentoSaude())
                                .build();
        }
}
