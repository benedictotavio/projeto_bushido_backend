package br.org.institutobushido.services.aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import br.org.institutobushido.controllers.dtos.aluno.AlunoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.AlunoDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.UpdateAlunoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.dados_escolares.UpdateDadosEscolaresDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.dados_sociais.DadosSociaisDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.dados_sociais.UpdateDadosSociaisDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.endereco.UpdateEnderecoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.GraduacaoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.GraduacaoDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.faltas.FaltaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.UpdateHistoricoSaudeDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.alergia.AlergiaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.cirurgia.CirurgiaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.doenca_cronica.DoencaCronicaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.uso_medicamento_continuo.UsoMedicamentoContinuoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.responsavel.ResponsavelDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.responsavel.ResponsavelDTOResponse;
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
import br.org.institutobushido.providers.enums.aluno.CorDePele;
import br.org.institutobushido.providers.enums.aluno.FiliacaoResposavel;
import br.org.institutobushido.providers.enums.aluno.Genero;
import br.org.institutobushido.providers.enums.aluno.Imovel;
import br.org.institutobushido.providers.enums.aluno.TipoSanguineo;
import br.org.institutobushido.providers.utils.resources.exceptions.AlreadyRegisteredException;
import br.org.institutobushido.providers.utils.resources.exceptions.EntityNotFoundException;
import br.org.institutobushido.repositories.AlunoRepositorio;
import br.org.institutobushido.repositories.TurmaRepositorio;

@SpringBootTest
class AlunoServiceTest {

        UpdateAlunoDTORequest updateAlunoDTORequest;

        @Mock
        private AlunoRepositorio alunoRepositorio;

        @Mock
        private TurmaRepositorio turmaRepositorio;

        @Mock
        private MongoTemplate mongoTemplate;

        private AlunoServices alunoServices;
        private AlunoDTORequest alunoDTORequest;
        private Aluno aluno;

        @BeforeEach
        void setUp() {

                aluno = new Aluno();

                aluno.setCpf("11111111111");
                aluno.setNome("Nome");
                aluno.setDataNascimento(new Date().getTime());
                aluno.setGenero(Genero.M);
                aluno.setTurma("Turma");
                aluno.setCartaoSus("CartaoSus");
                aluno.setEmail("Email");
                aluno.setTelefone("Telefone");
                aluno.setCorDePele(CorDePele.PRETO);

                aluno.setDadosEscolares(
                                new DadosEscolares("Escola"));

                aluno.setEndereco(
                                new Endereco(
                                                "CIDADE",
                                                "ESTADO",
                                                "CEP",
                                                "100",
                                                "LOGRADOURO"));

                aluno.setDadosSociais(
                                new DadosSociais(
                                        Imovel.PROPRIO,
                                        4,
                                        2,
                                        2000,
                                        false,
                                        false,
                                        false
                                ));

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
                                                LocalDate.now().plusMonths(3), false, 80, 1, 10));

                aluno.adicionarResponsavel(
                                new Responsavel("Nome", "12345678901", "Email", "Telefone", FiliacaoResposavel.OUTRO));

                alunoDTORequest = AlunoDTORequest.builder()
                                .withCpf(aluno.getCpf())
                                .withGraduacao(GraduacaoDTORequest.builder()
                                                .withKyu(aluno.getGraduacaoAtual().getKyu())
                                                .withDan(aluno.getGraduacaoAtual().getDan())
                                                .build())
                                .withResponsaveis(
                                                new ResponsavelDTORequest("NOME", "12345678910", "110000000",
                                                                "email@email.com", FiliacaoResposavel.MAE))
                                .withDadosSociais(new DadosSociaisDTORequest(aluno.getDadosSociais().isBolsaFamilia(),
                                                aluno.getDadosSociais().isAuxilioBrasil(),
                                                aluno.getDadosSociais().getImovel(),
                                                aluno.getDadosSociais().getNumerosDePessoasNaCasa(),
                                                aluno.getDadosSociais().getContribuintesDaRendaFamiliar(),
                                                aluno.getDadosSociais().isAlunoContribuiParaRenda(),
                                                aluno.getDadosSociais().getRendaFamiliar()))
                                .build();

                alunoServices = new AlunoServices(alunoRepositorio, mongoTemplate);
        }

        @Test
        void deveRetornarExceçãoSeRgEstiverRegistrado() {
                // Arrange
                when(mongoTemplate.find(any(Query.class), eq(Aluno.class))).thenReturn(List.of(aluno));
                when(alunoRepositorio.save(any(Aluno.class))).thenReturn(aluno);

                // Assert
                assertThrows(AlreadyRegisteredException.class, () -> alunoServices.adicionarAluno(alunoDTORequest));
        }

        @Test
        void deveCriarAluno() {
                // Arrange
                when(mongoTemplate.find(any(Query.class), eq(Aluno.class))).thenReturn(new ArrayList<>());
                when(alunoRepositorio.save(any(Aluno.class))).thenReturn(aluno);

                // Act
                String result = alunoServices.adicionarAluno(alunoDTORequest);
                // Assert
                assertEquals(aluno.getMatricula(), result);
        }

        @Test
        void deveRetornarExceçãoQuandoAlunoJaExistir() {

                when(mongoTemplate.find(any(Query.class), eq(Aluno.class))).thenReturn(List.of(aluno));

                // Assert
                assertThrows(AlreadyRegisteredException.class,
                                () -> alunoServices.adicionarAluno(alunoDTORequest));
        }

        @Test
        void deveBuscarAlunoPorCpf() {
                // Mocking data
                when(mongoTemplate.find(any(Query.class), eq(Aluno.class))).thenReturn(List.of(aluno));

                List<AlunoDTOResponse> result = alunoServices.buscarAluno(null, "123456789", 0, 10, "nome", "asc");

                assertEquals(1, result.size());
                assertEquals(result.get(0).graduacao().get(aluno.getGraduacao().size() - 1).fimGraduacao(),
                                LocalDate.now());
        }

        @Test
        void deveBuscarAlunoPorNome() {
                // Mocking data
                when(mongoTemplate.find(any(Query.class), eq(Aluno.class))).thenReturn(List.of(aluno));

                List<AlunoDTOResponse> result = alunoServices.buscarAluno("123456789", null, 0, 10, "nome", "asc");

                // Verify if the result is as expected
                assertEquals(1, result.size());
                assertEquals(result.get(0).graduacao().get(0).fimGraduacao(), LocalDate.now());
        }

        @Test
        void deveAdicionarResponsavel() {
                // Arrange
                String cpf = "validRg";
                ResponsavelDTORequest responsavelDTORequest = new ResponsavelDTORequest(
                                "Nome",
                                "12345678902",
                                "Email",
                                "Telefone",
                                FiliacaoResposavel.OUTRO);

                when(mongoTemplate.find(any(Query.class), eq(Aluno.class))).thenReturn(List.of(aluno));
                // Act
                ResponsavelDTOResponse result = alunoServices.adicionarResponsavel(cpf, responsavelDTORequest);

                // Assert
                assertNotNull(result);
        }

        @Test
        void deveRetornarExceçãoQuandoResponsavelNaoExistir() {
                // Arrange
                String cpf = "invalidRg";
                ResponsavelDTORequest responsavelDTORequest = new ResponsavelDTORequest(
                                "Nome",
                                "12345678903",
                                "Email",
                                "Telefone",
                                FiliacaoResposavel.OUTRO);

                // Act and Assert
                assertThrows(EntityNotFoundException.class, () -> {
                        alunoServices.adicionarResponsavel(cpf, responsavelDTORequest);
                });
        }

        @Test
        void deveRemoverResponsavel() {
                // Arrange
                String cpf = "12345678902";
                aluno.adicionarResponsavel(
                                new Responsavel("Nome", cpf, "Email", "Telefone", FiliacaoResposavel.OUTRO));

                when(mongoTemplate.find(any(Query.class), eq(Aluno.class))).thenReturn(List.of(aluno));

                // Act
                String result = alunoServices.removerResponsavel(aluno.getCpf(), cpf);

                // Assert
                assertEquals("1", result);
        }

        @Test
        void deveAdicionarFaltaAoAluno() {
                // Arrange
                Graduacao graduacao = new Graduacao(7, 0);
                graduacao.setFimGraduacao(LocalDate.now().plusMonths(2));
                aluno.adicionarGraduacao(graduacao);

                FaltaDTORequest faltaDTORequest = new FaltaDTORequest(
                                "Falta justificada",
                                "Falta");
                long dataFalta = new Date().getTime();

                when(mongoTemplate.find(any(Query.class), eq(Aluno.class))).thenReturn(List.of(aluno));

                // Act
                String result = alunoServices.adicionarFaltaDoAluno(aluno.getCpf(), faltaDTORequest, dataFalta);

                // Assert
                assertEquals("1", result);
                assertEquals(0, aluno.getGraduacao().get(0).getFaltas().size());
                assertTrue(aluno.getGraduacao().get(aluno.getGraduacao().size() - 1).isStatus());
        }

        @Test
        void deveRetirarFaltaDoAluno() {
                // Arrange
                String cpf = "123456789";

                Graduacao graduacao = new Graduacao(7, 0);
                graduacao.setFimGraduacao(LocalDate.now().plusMonths(2));
                graduacao.adicionarFalta("Motivo", "Observação",
                                Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
                                                .getTime());
                aluno.adicionarGraduacao(graduacao);

                when(mongoTemplate.find(any(Query.class), eq(Aluno.class))).thenReturn(List.of(aluno));

                // Act
                String result = alunoServices.retirarFaltaDoAluno(cpf, graduacao.getFaltas().get(0).getData());

                // Assert
                assertEquals("0", result);
                assertEquals(0, aluno.getGraduacao().get(0).getFaltas().size());
        }

        @Test
        void deveRetornarEdiçãoDeAlunoPorRg() throws IOException {
                // Arrange
                String cpf = "123456789";
                updateAlunoDTORequest = UpdateAlunoDTORequest.builder()
                                .withCartaoSus("11111111111")
                                .withCorDePele(CorDePele.BRANCO)
                                .withCpf(cpf)
                                .withDadosEscolares(
                                                new UpdateDadosEscolaresDTORequest("ESCOLA"))
                                .withDadosSociais(
                                                new UpdateDadosSociaisDTORequest(false, false, Imovel.PROPRIO, 4, 3,
                                                                false, 0))
                                .withDataNascimento(new Date().getTime() - 2000 * 60 * 60 * 24 * 4)
                                .withEmail("email@email.com")
                                .withEndereco(
                                                new UpdateEnderecoDTORequest("CIDADE", "ESTADO", "CEP", "100",
                                                                "LOGRADOURO"))
                                .withGenero(Genero.M)
                                .withHistoricoDeSaude(
                                                new UpdateHistoricoSaudeDTORequest(TipoSanguineo.O_POSITIVO,
                                                                new UsoMedicamentoContinuoDTORequest("MEDICAMENTO"),
                                                                new AlergiaDTORequest("ALERGIA"),
                                                                new CirurgiaDTORequest("CIRURGIA"),
                                                                new DoencaCronicaDTORequest("DOENCA")))
                                .withNome("NOME")
                                .withRg("564544498499")
                                .withTelefone("11111111111")
                                .withTurma("TURMA")
                                .build();

                when(mongoTemplate.find(any(Query.class), eq(Aluno.class))).thenReturn(List.of(aluno));

                // Act
                String result = alunoServices.editarAlunoPorMatricula(cpf, updateAlunoDTORequest);

                // Assert
                assertEquals("Aluno editado com sucesso!", result);
        }

        @Test
        void deveAprovarAluno() {
                int kyu = 7;
                when(mongoTemplate.find(any(Query.class), eq(Aluno.class))).thenReturn(List.of(aluno));
                aluno.setGraduacao(List.of(
                                new Graduacao(kyu, List.of(), true, 100, LocalDate.now().minusMonths(2),
                                                LocalDate.now().plusMonths(4), false, 0, 1, 10)));

                // Act
                GraduacaoDTOResponse result = alunoServices.aprovarAluno("123456789", 10);

                // Assert
                assertEquals(7, result.kyu());
                assertEquals(1, result.dan());
                assertFalse(result.status());
                assertTrue(result.aprovado());
        }

        @Test
        void deveReprovarAluno() {
                int kyu = 7;
                int dan = 1;
                when(mongoTemplate.find(any(Query.class), eq(Aluno.class))).thenReturn(List.of(aluno));

                aluno.setGraduacao(List.of(
                                new Graduacao(kyu, List.of(), true, 100, LocalDate.now().minusMonths(2),
                                                LocalDate.now().plusMonths(4), false, 0, dan, 10)));

                GraduacaoDTOResponse result = alunoServices.reprovarAluno(aluno.getCpf(), 5);

                // Assert
                assertNotNull(result);
                assertEquals(kyu, result.kyu());
                assertEquals(dan, result.dan());
                assertFalse(result.status());
                assertFalse(result.aprovado());
        }

        @Test
        void deveAprovarAlunoQuandoAlunoEstiverNoKyu1() {
                int kyu = 1;
                int dan = 1;
                when(mongoTemplate.find(any(Query.class), eq(Aluno.class))).thenReturn(List.of(aluno));

                aluno.setGraduacao(List.of(
                                new Graduacao(kyu, List.of(), true, 100, LocalDate.now().minusMonths(2),
                                                LocalDate.now().plusMonths(4), false, 0, dan, 10)));

                GraduacaoDTOResponse result = alunoServices.aprovarAluno(aluno.getCpf(), 9);

                // Assert
                assertNotNull(result);
                assertEquals(kyu, result.kyu());
                assertEquals(dan, result.dan());
                assertFalse(result.status());
                assertTrue(result.aprovado());
        }

        @Test
        void deveAprovarAdicionarDanAlunoQuandoAlunoEstiverNoKyu1() {
                int kyu = 1;
                int dan = 2;
                when(mongoTemplate.find(any(Query.class), eq(Aluno.class))).thenReturn(List.of(aluno));

                aluno.setGraduacao(List.of(
                                new Graduacao(kyu, List.of(), true, 100, LocalDate.now().minusMonths(2),
                                                LocalDate.now().plusMonths(4), false, 0, dan, 10)));

                GraduacaoDTOResponse result = alunoServices.aprovarAluno(aluno.getCpf(), 10);

                // Assert
                assertNotNull(result);
                assertEquals(kyu, result.kyu());
                assertEquals(dan, result.dan());
        }

        @Test
        void deveRetornarAlunoPorRg() {
                when(mongoTemplate.find(any(Query.class), eq(Aluno.class))).thenReturn(List.of(aluno));
                List<AlunoDTOResponse> result = alunoServices.buscarAlunoPorCpf("123456789");
                assertNotNull(result);
                assertEquals(1, result.size());
        }
}
