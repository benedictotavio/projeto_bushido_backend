package br.org.institutobushido.mappers.aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.institutobushido.controllers.dtos.aluno.AlunoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.AlunoDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.dados_escolares.DadosEscolaresDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.dados_sociais.DadosSociaisDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.endereco.EnderecoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.GraduacaoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.HistoricoSaudeDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.alergia.AlergiaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.cirurgia.CirurgiaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.doenca_cronica.DoencaCronicaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.uso_medicamento_continuo.UsoMedicamentoContinuoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.responsavel.ResponsavelDTORequest;
import br.org.institutobushido.enums.aluno.FiliacaoResposavel;
import br.org.institutobushido.enums.aluno.Genero;
import br.org.institutobushido.enums.aluno.Imovel;
import br.org.institutobushido.enums.aluno.TipoSanguineo;
import br.org.institutobushido.enums.aluno.Turno;
import br.org.institutobushido.models.aluno.Aluno;
import br.org.institutobushido.models.aluno.dados_escolares.DadosEscolares;
import br.org.institutobushido.models.aluno.dados_sociais.DadosSociais;
import br.org.institutobushido.models.aluno.endereco.Endereco;
import br.org.institutobushido.models.aluno.graduacao.Graduacao;
import br.org.institutobushido.models.aluno.historico_de_saude.HistoricoSaude;
import br.org.institutobushido.models.aluno.historico_de_saude.informacoes_saude.Alergia;
import br.org.institutobushido.models.aluno.historico_de_saude.informacoes_saude.Cirurgia;
import br.org.institutobushido.models.aluno.historico_de_saude.informacoes_saude.DoencaCronica;
import br.org.institutobushido.models.aluno.historico_de_saude.informacoes_saude.UsoMedicamentoContinuo;
import br.org.institutobushido.models.aluno.responsaveis.Responsavel;

@SpringBootTest
class AlunoMapperTest {
        private AlunoDTORequest alunoDTORequest;
        private Aluno aluno;
        private AlunoDTOResponse alunoDTOResponse;

        @BeforeEach
        void setUp() {
                alunoDTORequest = new AlunoDTORequest(
                                "John Doe",
                                new Date(),
                                Genero.OUTRO,
                                "TURMA",
                                new DadosSociaisDTORequest(
                                                false,
                                                false,
                                                Imovel.PROPRIO,
                                                5,
                                                2,
                                                false,
                                                0),
                                new DadosEscolaresDTORequest(
                                                Turno.MANHA,
                                                "ESCOLA",
                                                "SERIE"),
                                new EnderecoDTORequest(
                                                "CIDADE",
                                                "ESTADO",
                                                "CEP",
                                                "100"),
                                "123456789",
                                new ResponsavelDTORequest("Nome", "12345678901", "Email", "Telefone",
                                                FiliacaoResposavel.OUTRO),
                                new HistoricoSaudeDTORequest(
                                                TipoSanguineo.O_POSITIVO,
                                                new UsoMedicamentoContinuoDTORequest("Tipo"),
                                                new AlergiaDTORequest("Alergia"),
                                                new CirurgiaDTORequest("Cirurgia"),
                                                new DoencaCronicaDTORequest("Doenca"),
                                                List.of("Deficiencia"),
                                                List.of("Acompanhamento")),
                                new GraduacaoDTORequest(7, 1));

                aluno = new Aluno(
                                "123456789",
                                "John Doe",
                                new Date(),
                                Genero.OUTRO,
                                "TURMA");

                aluno.setDadosEscolares(
                                new DadosEscolares(
                                                Turno.MANHA,
                                                "ESCOLA",
                                                "SERIE"));

                aluno.setEndereco(
                                new Endereco(
                                                "CIDADE",
                                                "ESTADO",
                                                "CEP",
                                                "100"));

                aluno.setDadosSociais(
                                new DadosSociais(
                                                false,
                                                false,
                                                Imovel.PROPRIO,
                                                5,
                                                2,
                                                false,
                                                0));

                aluno.setHistoricoSaude(
                                new HistoricoSaude(
                                                TipoSanguineo.O_POSITIVO,
                                                new UsoMedicamentoContinuo("Tipo"),
                                                new DoencaCronica("Doenca"),
                                                new Alergia("Alergia"),
                                                new Cirurgia("Cirurgia"),
                                                List.of("Deficiencia"),
                                                List.of("Acompanhamento")));

                aluno.adicionarGraduacao(
                                new Graduacao(7, new ArrayList<>(), true, 100, LocalDate.now().minusMonths(3),
                                                LocalDate.now().plusMonths(3), false, 80, 1));

                aluno.adicionarResponsavel(
                                new Responsavel("Nome", "12345678901", "Email", "Telefone", FiliacaoResposavel.OUTRO));
        }

        @Test
        void deveMapearAlunoDTORequestToAluno() {
                aluno = AlunoMapper.mapToAluno(alunoDTORequest);

                assertEquals(alunoDTORequest.nome(), aluno.getNome());
                assertEquals(alunoDTORequest.rg(), aluno.getRg());
                assertEquals(alunoDTORequest.dataNascimento(), aluno.getDataNascimento());
                assertEquals(alunoDTORequest.genero(), aluno.getGenero());
                assertEquals(alunoDTORequest.dadosSociais().auxilioBrasil(), aluno.getDadosSociais().isAuxilioBrasil());
                assertEquals(alunoDTORequest.dadosEscolares().escola(), aluno.getDadosEscolares().getEscola());
                assertEquals(alunoDTORequest.endereco().estado(), aluno.getEndereco().getEstado());
                assertEquals(alunoDTORequest.responsaveis().email(), aluno.getResponsaveis().get(0).getEmail());
                assertEquals(alunoDTORequest.historicoSaude().alergia().tipo(),
                                aluno.getHistoricoSaude().getAlergia().getTipo());
        }

        @Test
        void deveMapearAlunoDTOResponseDeAluno() {
                alunoDTOResponse = AlunoMapper.mapToAlunoDTOResponse(aluno);
                assertEquals(aluno.getNome(), alunoDTOResponse.nome());
                assertEquals(aluno.getRg(), alunoDTOResponse.rg());
                assertEquals(aluno.getDataNascimento(), alunoDTOResponse.dataNascimento());
                assertEquals(aluno.getGenero(), alunoDTOResponse.genero());
                assertEquals(aluno.getDadosSociais().isAuxilioBrasil(),
                                alunoDTOResponse.dadosSociais().auxilioBrasil());
                assertEquals(aluno.getDadosEscolares().getEscola(), alunoDTOResponse.dadosEscolares().escola());
                assertEquals(aluno.getEndereco().getEstado(), alunoDTOResponse.endereco().estado());
                assertEquals(aluno.getHistoricoSaude().getAlergia().getTipo(),
                                alunoDTOResponse.historicoSaude().alergia().tipo());
        }

        @Test
        void deveMapearUmaListaAlunoParaUmaListaAlunoDTOResponse() {
                List<Aluno> alunos = List.of(aluno);
                List<AlunoDTOResponse> alunosDTOResponses = AlunoMapper.mapToListAlunoDTOResponse(alunos);
                assertEquals(alunos.get(0).getNome(), alunosDTOResponses.get(0).nome());
                assertEquals(alunos.get(0).getRg(), alunosDTOResponses.get(0).rg());
                assertEquals(alunos.get(0).getDataNascimento(), alunosDTOResponses.get(0).dataNascimento());
                assertEquals(alunos.get(0).getGenero(), alunosDTOResponses.get(0).genero());
        }

}
