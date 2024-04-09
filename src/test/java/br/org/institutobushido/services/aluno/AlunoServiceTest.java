package br.org.institutobushido.services.aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import br.org.institutobushido.controllers.dtos.aluno.AlunoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.AlunoDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.UpdateAlunoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.dados_escolares.DadosEscolaresDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.dados_escolares.UpdateDadosEscolaresDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.dados_sociais.DadosSociaisDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.dados_sociais.UpdateDadosSociaisDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.endereco.EnderecoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.endereco.UpdateEnderecoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.GraduacaoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.GraduacaoDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.faltas.FaltaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.HistoricoSaudeDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.UpdateHistoricoSaudeDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.alergia.AlergiaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.cirurgia.CirurgiaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.doenca_cronica.DoencaCronicaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.uso_medicamento_continuo.UsoMedicamentoContinuoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.responsavel.ResponsavelDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.responsavel.ResponsavelDTOResponse;
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
import br.org.institutobushido.models.turma.Turma;
import br.org.institutobushido.repositories.AlunoRepositorio;
import br.org.institutobushido.repositories.TurmaRepositorio;
import br.org.institutobushido.resources.exceptions.AlreadyRegisteredException;
import br.org.institutobushido.resources.exceptions.EntityNotFoundException;

@SpringBootTest
class AlunoServiceTest {

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
        public void setUp() {
                alunoDTORequest = new AlunoDTORequest(
                                "John Doe",
                                new Date(),
                                Genero.OUTRO,
                                "Turma A",
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
                                new GraduacaoDTORequest(7, 2));

                aluno = new Aluno(
                                "123456789",
                                "John Doe",
                                new Date(),
                                Genero.OUTRO,
                                "Turma A");

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
                                new Graduacao(7, 0));

                aluno.adicionarResponsavel(
                                new Responsavel("Nome", "12345678901", "Email", "Telefone", FiliacaoResposavel.OUTRO));

                alunoServices = new AlunoServices(alunoRepositorio, mongoTemplate, turmaRepositorio);
        }

        @Test
        void deveCriarAluno() {

                when(alunoRepositorio.findByRg(anyString())).thenReturn(Optional.empty());
                when(turmaRepositorio.findByNome(anyString()))
                                .thenReturn(Optional.of(new Turma("Endereço II", "Turma A", "Tutor")));
                when(alunoRepositorio.save(any(Aluno.class))).thenReturn(aluno);

                // Act
                String rg = alunoServices.adicionarAluno(alunoDTORequest);

                // Assert
                assertEquals(alunoDTORequest.rg(), rg);
                // Verify that the repository's save method was called
                verify(alunoRepositorio, times(1)).save(any(Aluno.class));
        }

        @Test
        void deveRetornarExceçãoQuandoAlunoJaExistir() {

                when(alunoRepositorio.findByRg(anyString())).thenReturn(Optional.of(aluno));

                // Assert
                assertThrows(AlreadyRegisteredException.class,
                                () -> alunoServices.adicionarAluno(alunoDTORequest));
        }

        @Test
        void testBuscarAlunoPorRg() {
                // Mocking data
                when(mongoTemplate.find(Mockito.any(Query.class), Mockito.eq(Aluno.class))).thenReturn(List.of(aluno));

                List<AlunoDTOResponse> result = alunoServices.buscarAluno(null, "123456789", 0, 10, "nome", "asc");

                assertEquals(1, result.size());
        }

        @Test
        void testBuscarAlunoPorNome() {
                // Mocking data
                when(mongoTemplate.find(Mockito.any(Query.class), Mockito.eq(Aluno.class))).thenReturn(List.of(aluno));

                List<AlunoDTOResponse> result = alunoServices.buscarAluno("123456789", null, 0, 10, "nome", "asc");

                // Verify if the result is as expected
                assertEquals(1, result.size());
        }

        @Test
        void deveAdicionarResponsavel() {
                // Arrange
                String rg = "validRg";
                ResponsavelDTORequest responsavelDTORequest = new ResponsavelDTORequest(
                                "Nome",
                                "12345678902",
                                "Email",
                                "Telefone",
                                FiliacaoResposavel.OUTRO);

                when(mongoTemplate.find(Mockito.any(Query.class), Mockito.eq(Aluno.class))).thenReturn(List.of(aluno));
                // Act
                ResponsavelDTOResponse result = alunoServices.adicionarResponsavel(rg, responsavelDTORequest);

                // Assert
                assertNotNull(result);
        }

        @Test
        void deveRetornarExceçãoQuandoResponsavelNaoExistir() {
                // Arrange
                String rg = "invalidRg";
                ResponsavelDTORequest responsavelDTORequest = new ResponsavelDTORequest(
                                "Nome",
                                "12345678903",
                                "Email",
                                "Telefone",
                                FiliacaoResposavel.OUTRO);

                // Act and Assert
                assertThrows(EntityNotFoundException.class, () -> {
                        alunoServices.adicionarResponsavel(rg, responsavelDTORequest);
                });
        }

        @Test
        void deveRemoverResponsavel() {
                // Arrange
                String cpf = "12345678902";
                aluno.adicionarResponsavel(
                                new Responsavel("Nome", cpf, "Email", "Telefone", FiliacaoResposavel.OUTRO));

                when(mongoTemplate.find(Mockito.any(Query.class), Mockito.eq(Aluno.class))).thenReturn(List.of(aluno));

                // Act
                String result = alunoServices.removerResponsavel(aluno.getRg(), cpf);

                // Assert
                assertEquals("1", result);
        }

        @Test
        void deveAdicionarFaltaAoAluno() {
                // Arrange
                Graduacao graduacao = new Graduacao(7, 0);
                graduacao.setInicioGraduacao(LocalDate.now().minusMonths(2));
                aluno.adicionarGraduacao(graduacao);

                FaltaDTORequest faltaDTORequest = new FaltaDTORequest(
                                "Falta justificada",
                                "Falta");
                long dataFalta = new Date().getTime();

                when(mongoTemplate.find(Mockito.any(Query.class), Mockito.eq(Aluno.class))).thenReturn(List.of(aluno));

                // Act
                String result = alunoServices.adicionarFaltaDoAluno(aluno.getRg(), faltaDTORequest, dataFalta);

                // Assert
                assertEquals("1", result);
                assertEquals(0, aluno.getGraduacao().get(0).getFaltas().size());
        }

        @Test
        void deveRetirarFaltaDoAluno() {
                // Arrange
                String rg = "123456789";

                Graduacao graduacao = new Graduacao(7, 0);
                graduacao.setInicioGraduacao(LocalDate.now().minusMonths(2));
                graduacao.adicionarFalta("Motivo", "Observação",
                                Date.from(LocalDate.now().minusDays(3).atStartOfDay(ZoneId.systemDefault()).toInstant())
                                                .getTime());
                aluno.adicionarGraduacao(graduacao);

                when(mongoTemplate.find(Mockito.any(Query.class), Mockito.eq(Aluno.class))).thenReturn(List.of(aluno));

                // Act
                String result = alunoServices.retirarFaltaDoAluno(rg, graduacao.getFaltas().get(0).getData());

                // Assert
                assertEquals("0", result);
                assertEquals(0, aluno.getGraduacao().get(0).getFaltas().size());
        }

        @Test
        void deveRetornarEdiçãoDeAlunoPorRg() {
                // Arrange
                String rg = "123456789";
                UpdateAlunoDTORequest updateAlunoDTORequest = new UpdateAlunoDTORequest(
                                new UpdateDadosSociaisDTORequest(false, false, null, 5, 2, false, 0),
                                new UpdateDadosEscolaresDTORequest(Turno.TARDE, "Escola", "Serie"),
                                new UpdateEnderecoDTORequest(
                                                "Cidade", "Estado", "CEP", "Numero"),
                                new UpdateHistoricoSaudeDTORequest(null, null, null, null, null));

                when(mongoTemplate.find(Mockito.any(Query.class), Mockito.eq(Aluno.class))).thenReturn(List.of(aluno));

                // Act
                String result = alunoServices.editarAlunoPorRg(rg, updateAlunoDTORequest);

                // Assert
                assertEquals("Aluno editado com sucesso!", result);
        }

        @Test
        void deveAprovarAluno() {
                int kyu = 7;
                when(mongoTemplate.find(any(Query.class), eq(Aluno.class))).thenReturn(List.of(aluno));
                aluno.setGraduacao(List.of(
                                new Graduacao(kyu, List.of(), true, 100, LocalDate.now().minusMonths(2),
                                                LocalDate.now().plusMonths(4), false, 0, 1)));

                // Act
                GraduacaoDTOResponse result = alunoServices.aprovarAluno("123456789");

                // Assert
                assertEquals(kyu - 1, result.kyu());
                assertEquals(1, result.dan());
        }

        @Test
        void deveReprovarAluno() {
                int kyu = 7;
                int dan = 1;
                when(mongoTemplate.find(any(Query.class), eq(Aluno.class))).thenReturn(List.of(aluno));

                aluno.setGraduacao(List.of(
                                new Graduacao(kyu, List.of(), true, 100, LocalDate.now().minusMonths(2),
                                                LocalDate.now().plusMonths(4), false, 0, 1)));

                GraduacaoDTOResponse result = alunoServices.reprovarAluno(aluno.getRg());

                // Assert
                assertNotNull(result);
                assertEquals(kyu, result.kyu());
                assertEquals(dan, result.dan());
        }

        @Test
        void deveAprovarAlunoQuandoAlunoEstiverNoKyu1() {
                int kyu = 1;
                int dan = 1;
                when(mongoTemplate.find(any(Query.class), eq(Aluno.class))).thenReturn(List.of(aluno));

                aluno.setGraduacao(List.of(
                                new Graduacao(kyu, List.of(), true, 100, LocalDate.now().minusMonths(2),
                                                LocalDate.now().plusMonths(4), false, 0, 1)));

                GraduacaoDTOResponse result = alunoServices.aprovarAluno(aluno.getRg());

                // Assert
                assertNotNull(result);
                assertEquals(kyu, result.kyu());
                assertEquals(dan + 1, result.dan());
        }
}