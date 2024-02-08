package br.org.institutobushido.mappers;

import br.org.institutobushido.dtos.aluno.historico_de_saude.HistoricoSaudeDTORequest;
import br.org.institutobushido.dtos.aluno.historico_de_saude.HistoricoSaudeDTOResponse;
import br.org.institutobushido.dtos.aluno.historico_de_saude.informacoes_de_saude.alergia.AlergiaDTOResponse;
import br.org.institutobushido.dtos.aluno.historico_de_saude.informacoes_de_saude.cirurgia.CirurgiaDTOResponse;
import br.org.institutobushido.dtos.aluno.historico_de_saude.informacoes_de_saude.doenca_cronica.DoencaCronicaDTOResponse;
import br.org.institutobushido.dtos.aluno.historico_de_saude.informacoes_de_saude.uso_medicamento_continuo.UsoMedicamentoContinuoDTOResponse;
import br.org.institutobushido.model.aluno.historico_de_saude.HistoricoSaude;
import br.org.institutobushido.model.aluno.historico_de_saude.informacoes_saude.Alergias;
import br.org.institutobushido.model.aluno.historico_de_saude.informacoes_saude.Cirurgias;
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

                historicoSaude.setAlergias(
                                new Alergias(historicoSaudeDTORequest.alergia().resposta(),
                                                historicoSaudeDTORequest.alergia().tipo()));
                historicoSaude.setCirurgias(new Cirurgias(historicoSaudeDTORequest.cirurgia().resposta(),
                                historicoSaudeDTORequest.cirurgia().tipo()));
                historicoSaude.setDoencaCronica(new DoencaCronica(historicoSaudeDTORequest.doencaCronica().resposta(),
                                historicoSaudeDTORequest.doencaCronica().tipo()));
                historicoSaude.setUsoMedicamentoContinuo(new UsoMedicamentoContinuo(
                                historicoSaudeDTORequest.usoMedicamentoContinuo().resposta(),
                                historicoSaudeDTORequest.usoMedicamentoContinuo().tipo(),
                                historicoSaudeDTORequest.usoMedicamentoContinuo().qualMedicamento()));
                historicoSaude.setDeficiencias(historicoSaudeDTORequest.deficiencia());
                historicoSaude.setAcompanhamentoSaude(historicoSaudeDTORequest.acompanhamentoSaude());
                return historicoSaude;
        }

        public static HistoricoSaudeDTOResponse mapToHistoricoSaudeDTOResponse(HistoricoSaude historicoSaude) {
                if (historicoSaude == null) {
                        return null;
                }
                return HistoricoSaudeDTOResponse.builder()
                                .withAlergias(AlergiaDTOResponse.builder()
                                                .withResposta(historicoSaude.getAlergias().getResposta())
                                                .withTipo(historicoSaude.getAlergias().getTipo()).build())
                                .withCirurgias(CirurgiaDTOResponse.builder()
                                                .withResposta(historicoSaude.getCirurgias().getResposta())
                                                .withTipo(historicoSaude.getCirurgias().getTipo()).build())
                                .withDoencaCronica(
                                                DoencaCronicaDTOResponse.builder()
                                                                .withResposta(historicoSaude.getDoencaCronica()
                                                                                .getResposta())
                                                                .withTipo(historicoSaude.getDoencaCronica().getTipo())
                                                                .build())
                                .withUsoMedicamento(UsoMedicamentoContinuoDTOResponse.builder()
                                                .withQualMedicamento(historicoSaude.getUsoMedicamentoContinuo()
                                                                .getQualMedicamento())
                                                .withResposta(historicoSaude.getUsoMedicamentoContinuo().getResposta())
                                                .withTipo(historicoSaude.getUsoMedicamentoContinuo().getTipo()).build())
                                .withDeficiencias(historicoSaude.getDeficiencias())
                                .withAcompanhamentoSaude(historicoSaude.getAcompanhamentoSaude())
                                .build();
        }
}
