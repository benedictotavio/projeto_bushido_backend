package br.org.institutobushido.controllers.routes.aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
import br.org.institutobushido.controllers.response.success.SuccessDeleteResponse;
import br.org.institutobushido.controllers.response.success.SuccessPostResponse;
import br.org.institutobushido.controllers.response.success.SuccessPutResponse;
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
import br.org.institutobushido.services.aluno.AlunoServicesInterface;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(SpringExtension.class)
class AlunoControllerTest {
        private AlunoDTORequest alunoDTORequest;
        private AlunoDTOResponse alunoDTOResponse;
        private Aluno aluno;
        private UpdateAlunoDTORequest updateAlunoDTORequest;
        private ResponsavelDTORequest responsavelDTORequest;
        private GraduacaoDTOResponse graduacaoDTOResponse;
        @InjectMocks
        private AlunoController alunoController;

        @Mock
        private AlunoServicesInterface alunoServices;

        @BeforeEach
        void setUp() {
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
                                                "100",
                                                "LOGRADOURO"));

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
                                                LocalDate.now().plusMonths(3), false, 80, 1, 10));

                aluno.adicionarResponsavel(
                                new Responsavel("Nome", "12345678901", "Email", "Telefone", FiliacaoResposavel.OUTRO));

                alunoDTORequest = new AlunoDTORequest(
                                "John Doe",
                                new Date(new Date().getTime() - 2000 * 60 * 60 * 24 * 4).getTime(),
                                Genero.OUTRO,
                                "Turma A",
                                new DadosSociaisDTORequest(
                                                false,
                                                false,
                                                Imovel.PROPRIO,
                                                5,
                                                2,
                                                false,
                                                1000),
                                new DadosEscolaresDTORequest(
                                                Turno.MANHA,
                                                "ESCOLA",
                                                "SERIE"),
                                new EnderecoDTORequest(
                                                "CIDADE",
                                                "ESTADO",
                                                "CEP",
                                                "100",
                                                "LOGRADOURO"),
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

                alunoDTOResponse = AlunoDTOResponse.builder()
                                .withCpf(aluno.getCpf())
                                .withNome(aluno.getNome())
                                .withDataNascimento(aluno.getDataNascimento())
                                .withGenero(aluno.getGenero())
                                .withTurma(aluno.getTurma())
                                .build();

                graduacaoDTOResponse = new GraduacaoDTOResponse(
                                7,
                                List.of(),
                                true,
                                LocalDate.now().minusMonths(3),
                                LocalDate.now().plusMonths(3),
                                100,
                                false,
                                80,
                                1,
                                10);
        }

        @Test
        void deveCriarAluno() throws URISyntaxException, IOException {

                // Act
                when(alunoServices.adicionarAluno(alunoDTORequest)).thenReturn(aluno.getCpf());
                ResponseEntity<SuccessPostResponse> response = alunoController.adicionarAluno(alunoDTORequest);

                assertEquals(HttpStatus.CREATED, response.getStatusCode());

                verify(alunoServices).adicionarAluno(alunoDTORequest);

                URI expectedUri = new URI("/api/V1/aluno");
                assertEquals(expectedUri, response.getHeaders().getLocation());

                SuccessPostResponse responseBody = response.getBody();

                assert responseBody != null;
                assertEquals(aluno.getCpf(), responseBody.getId());
                assertEquals("Aluno adicionado com sucesso", responseBody.getMessage());
                assertEquals("Aluno", responseBody.getEntity());
        }

        @Test
        void deveBuscarAluno() {
                // Arrange
                String nome = "John";
                String cpf = "123456";
                int page = 0;
                int size = 10;
                String sortBy = "nome";
                String sortOrder = "asc";// Add some mock aluno objects

                // Mock the service method to return a list of alunos
                when(alunoServices.buscarAluno(nome, cpf, page, size, sortOrder, sortBy))
                                .thenReturn(List.of(alunoDTOResponse));

                // Act
                ResponseEntity<List<AlunoDTOResponse>> responseEntity = alunoController.buscarAluno(cpf, nome, page,
                                size, sortBy, sortOrder);

                // Assert
                assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
                assertEquals(List.of(alunoDTOResponse), responseEntity.getBody());
        }

        @Test
        void deveEditarAluno() throws IOException {
                updateAlunoDTORequest = new UpdateAlunoDTORequest(
                                "NOME 1",
                                new Date().getTime(),
                                Genero.M,
                                "TURMA 1",
                                new UpdateDadosSociaisDTORequest(
                                                false,
                                                false,
                                                Imovel.PROPRIO,
                                                5,
                                                2,
                                                false,
                                                1000),
                                new UpdateDadosEscolaresDTORequest(
                                                Turno.MANHA,
                                                "ESCOLA",
                                                "SERIE"),
                                new UpdateEnderecoDTORequest(
                                                "CIDADE",
                                                "ESTADO",
                                                "CEP",
                                                "100",
                                                ""),
                                new UpdateHistoricoSaudeDTORequest(
                                                TipoSanguineo.O_POSITIVO,
                                                new UsoMedicamentoContinuoDTORequest("Tipo"),
                                                new AlergiaDTORequest("Alergia"),
                                                new CirurgiaDTORequest("Cirurgia"),
                                                new DoencaCronicaDTORequest("Doenca")));

                when(alunoServices.editarAlunoPorCpf(aluno.getCpf(), updateAlunoDTORequest))
                                .thenReturn("Aluno editado com sucesso!");

                // Act
                ResponseEntity<SuccessPutResponse> responseEntity = alunoController.editarAluno(aluno.getCpf(),
                                updateAlunoDTORequest);

                // Assert
                assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

                // Verify that the service method was called with the correct arguments
                verify(alunoServices).editarAlunoPorCpf(aluno.getCpf(), updateAlunoDTORequest);

                // Verify response body
                SuccessPutResponse responseBody = responseEntity.getBody();
                assert responseBody != null;
                assertEquals(aluno.getCpf(), responseBody.getId());
                assertEquals("Aluno editado com sucesso!", responseBody.getMessage());
                assertEquals("Aluno", responseBody.getEntity());
        }

        @Test
        void deveAdicnarUmNovoResponsavel() {
                responsavelDTORequest = new ResponsavelDTORequest(
                                "123456",
                                "Nome",
                                "CPF",
                                "RG",
                                FiliacaoResposavel.OUTRO);

                when(alunoServices.adicionarResponsavel(aluno.getCpf(), responsavelDTORequest))
                                .thenReturn(new ResponsavelDTOResponse(
                                                responsavelDTORequest.nome(),
                                                responsavelDTORequest.cpf(),
                                                responsavelDTORequest.telefone(),
                                                responsavelDTORequest.email(),
                                                responsavelDTORequest.filiacao().name()));

                // Act
                ResponseEntity<SuccessPostResponse> responseEntity = alunoController.adicionarResponsavel(aluno.getCpf(),
                                responsavelDTORequest);

                // Assert
                assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

                // Verify that the service method was called with the correct arguments
                verify(alunoServices).adicionarResponsavel(aluno.getCpf(), responsavelDTORequest);

                // Verify response body
                SuccessPostResponse responseBody = responseEntity.getBody();
                assert responseBody != null;
                assertEquals(responsavelDTORequest.cpf(), responseBody.getId());
                assertEquals("Responsável adicionado com sucesso", responseBody.getMessage());
                assertEquals("Responsavel", responseBody.getEntity());

        }

        @Test
        void deveRemoverResponsavel() {
                // Arrange
                responsavelDTORequest = new ResponsavelDTORequest(
                                "123456",
                                "Nome",
                                "CPF",
                                "RG",
                                FiliacaoResposavel.OUTRO);

                // Mock service method
                when(alunoServices.removerResponsavel(aluno.getCpf(), responsavelDTORequest.cpf()))
                                .thenReturn(String.valueOf(aluno.getResponsaveis().size()));

                // Act
                ResponseEntity<SuccessDeleteResponse> responseEntity = alunoController.removerResponsavel(aluno.getCpf(),
                                responsavelDTORequest.cpf());

                // Assert
                assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

                // Verify that the service method was called with the correct arguments
                verify(alunoServices).removerResponsavel(aluno.getCpf(), responsavelDTORequest.cpf());

                // Verify response body
                SuccessDeleteResponse responseBody = responseEntity.getBody();
                assert responseBody != null;
                assertEquals(String.valueOf(aluno.getResponsaveis().size()), responseBody.getId());
                assertEquals("Responsável removido com sucesso", responseBody.getMessage());
                assertEquals("Responsavel", responseBody.getEntity());
        }

        @Test
        void deveAdicionarFaltaAoAluno() {
                String cpf = "123456789";
                long data = System.currentTimeMillis();
                FaltaDTORequest faltaDTORequest = new FaltaDTORequest(
                                "Motivo",
                                "Tipo");

                String additionMessage = String.valueOf(aluno.getGraduacao().size()); // Mock addition message

                // Mock service method
                when(alunoServices.adicionarFaltaDoAluno(cpf, faltaDTORequest, data)).thenReturn(additionMessage);

                // Act
                ResponseEntity<SuccessPostResponse> responseEntity = alunoController
                                .adicionarFaltaAoAluno(faltaDTORequest, cpf, data);

                // Assert
                assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

                // Verify that the service method was called with the correct arguments
                verify(alunoServices).adicionarFaltaDoAluno(cpf, faltaDTORequest, data);

                // Verify response body
                SuccessPostResponse responseBody = responseEntity.getBody();
                assert responseBody != null;
                assertEquals(additionMessage, responseBody.getId());
                assertEquals("Falta adicionada", responseBody.getMessage());
                assertEquals("Falta", responseBody.getEntity());
        }

        @Test
        void deveRetirarFaltaAoAluno() {
                // Arrange
                String cpf = "123456789";
                String data = "2024-04-18"; // Mock data value

                String removalMessage = "0"; // Mock removal message

                // Mock service method
                when(alunoServices.retirarFaltaDoAluno(cpf, data)).thenReturn(removalMessage);

                // Act
                ResponseEntity<SuccessDeleteResponse> responseEntity = alunoController.retirarFaltaAoAluno(data, cpf);

                // Assert
                assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

                // Verify that the service method was called with the correct arguments
                verify(alunoServices).retirarFaltaDoAluno(cpf, data);

                // Verify response body
                SuccessDeleteResponse responseBody = responseEntity.getBody();
                assert responseBody != null;
                assertEquals(removalMessage, responseBody.getId());
                assertEquals("Falta retirada com sucesso", responseBody.getMessage());
                assertEquals("Falta", responseBody.getEntity());
        }

        @Test
        void deveAdicionarDeficiencia() {
                // Arrange
                String cpf = "123456789";
                String deficiencia = "Visual impairment";

                // Mock service method
                when(alunoServices.adicionarDeficiencia(cpf, deficiencia)).thenReturn(deficiencia);

                // Act
                ResponseEntity<SuccessPostResponse> responseEntity = alunoController.adicionarDeficiencia(cpf,
                                deficiencia);

                // Assert
                assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

                // Verify that the service method was called with the correct arguments
                verify(alunoServices).adicionarDeficiencia(cpf, deficiencia);

                // Verify response body
                SuccessPostResponse responseBody = responseEntity.getBody();
                assert responseBody != null;
                assertEquals(deficiencia, responseBody.getId());
                assertEquals("Deficiência adicionada", responseBody.getMessage());
                assertEquals("HistoricoSaude", responseBody.getEntity());
        }

        @Test
        void deveRemoverDeficiencia() {
                // Arrange
                String cpf = "123456789";
                String deficiencia = "Visual impairment"; // Mock removal message

                // Mock service method
                when(alunoServices.removerDeficiencia(cpf, deficiencia)).thenReturn(deficiencia);

                // Act
                ResponseEntity<SuccessDeleteResponse> responseEntity = alunoController.removerDeficiencia(cpf,
                                deficiencia);

                // Assert
                assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

                // Verify that the service method was called with the correct arguments
                verify(alunoServices).removerDeficiencia(cpf, deficiencia);

                // Verify response body
                SuccessDeleteResponse responseBody = responseEntity.getBody();
                assert responseBody != null;
                assertEquals(deficiencia, responseBody.getId());
                assertEquals("Deficiência " + deficiencia + " foi removida com sucesso.", responseBody.getMessage());
                assertEquals("HistoricoSaude", responseBody.getEntity());
        }

        @Test
        void deveAdicionarAcompanhamentoSaude() {
                // Arrange
                String cpf = "123456789";
                String acompanhamento = "Physical therapy";

                // Mock service method
                when(alunoServices.adicionarAcompanhamentoSaude(cpf, acompanhamento)).thenReturn(acompanhamento);

                // Act
                ResponseEntity<SuccessPostResponse> responseEntity = alunoController.adicionarAcompanhamentoSaude(cpf,
                                acompanhamento);

                // Assert
                assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

                // Verify that the service method was called with the correct arguments
                verify(alunoServices).adicionarAcompanhamentoSaude(cpf, acompanhamento);

                // Verify response body
                SuccessPostResponse responseBody = responseEntity.getBody();
                assert responseBody != null;
                assertEquals(acompanhamento, responseBody.getId());
                assertEquals("Acompanhamento " + acompanhamento + " foi adicionado com sucesso.",
                                responseBody.getMessage());
                assertEquals("HistoricoSaude", responseBody.getEntity());
        }

        @Test
        void deveAprovarAluno() {
                // Arrange
                String cpf = "123456789";
                int nota = 10;

                // Mock service method
                when(alunoServices.aprovarAluno(cpf, nota)).thenReturn(graduacaoDTOResponse);

                // Act
                ResponseEntity<SuccessPostResponse> responseEntity = alunoController.aprovarAluno(cpf, nota);

                // Assert
                assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

                // Verify that the service method was called with the correct argument
                verify(alunoServices).aprovarAluno(cpf, nota);

                // Verify response body
                SuccessPostResponse responseBody = responseEntity.getBody();
                assert responseBody != null;
                assertEquals(String.valueOf(graduacaoDTOResponse.kyu()), responseBody.getId());
                assertEquals("Graduação concluída com sucesso.", responseBody.getMessage());
                assertEquals("Graduacao", responseBody.getEntity());
        }

        @Test
        void deveReprovarAluno() {
                // Arrange
                String cpf = "123456789";
                int nota = 5;

                // Mock service method
                when(alunoServices.reprovarAluno(cpf, nota)).thenReturn(graduacaoDTOResponse);

                // Act
                ResponseEntity<SuccessPostResponse> responseEntity = alunoController.reprovarAluno(cpf, nota);

                // Assert
                assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

                // Verify that the service method was called with the correct argument
                verify(alunoServices).reprovarAluno(cpf, nota);

                // Verify response body
                SuccessPostResponse responseBody = responseEntity.getBody();
                assert responseBody != null;
                assertEquals(String.valueOf(graduacaoDTOResponse.kyu()), responseBody.getId());
                assertEquals("Graduação concluída com sucesso.", responseBody.getMessage());
                assertEquals("Graduacao", responseBody.getEntity());
        }

        @Test
        void deveRemoverAcompanhamentoSaude() {
                // Arrange
                String cpf = "123456789";
                String acompanhamento = "Physical therapy";

                // Mock service method
                when(alunoServices.removerAcompanhamentoSaude(cpf, acompanhamento)).thenReturn(acompanhamento);

                // Act
                ResponseEntity<SuccessDeleteResponse> responseEntity = alunoController.removerAcompanhamentoSaude(cpf,
                                acompanhamento);

                // Assert
                assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
                verify(alunoServices).removerAcompanhamentoSaude(cpf, acompanhamento);

                // Verify response body
                SuccessDeleteResponse responseBody = responseEntity.getBody();
                assert responseBody != null;
                assertEquals(acompanhamento, responseBody.getId());
                assertEquals("Acamponhamento " + acompanhamento + " foi removido com sucesso.",
                                responseBody.getMessage());
        }
}


