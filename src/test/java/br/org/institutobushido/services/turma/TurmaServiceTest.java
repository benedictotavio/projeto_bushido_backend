package br.org.institutobushido.services.turma;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import br.org.institutobushido.controllers.dtos.turma.TurmaDTORequest;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import br.org.institutobushido.controllers.dtos.turma.TurmaAlunoDTOResponse;
import br.org.institutobushido.controllers.dtos.turma.TurmaDTOResponse;
import br.org.institutobushido.enums.admin.UserRole;
import br.org.institutobushido.enums.aluno.Genero;
import br.org.institutobushido.models.admin.Admin;
import br.org.institutobushido.models.admin.turmas.TurmaResponsavel;
import br.org.institutobushido.models.turma.Turma;
import br.org.institutobushido.repositories.AdminRepositorio;
import br.org.institutobushido.repositories.TurmaRepositorio;
import br.org.institutobushido.resources.exceptions.AlreadyRegisteredException;
import br.org.institutobushido.resources.exceptions.EntityNotFoundException;
import br.org.institutobushido.resources.exceptions.LimitQuantityException;

@SpringBootTest
class TurmaServiceTest {

    @Mock
    private TurmaRepositorio turmaRepositorio;

    @Mock
    private AdminRepositorio adminRepositorio;

    @Mock
    private MongoTemplate mongoTemplate;

    private TurmaResponsavel turmaResponsavel;
    private TurmaService turmaServices;
    private TurmaDTORequest turmaDTORequest;
    private TurmaDTOResponse turmaDTOResponse;
    private Turma turma;
    private Admin admin;

    @BeforeEach
    void setUp() {
        turmaServices = new TurmaService(turmaRepositorio, mongoTemplate, adminRepositorio);
        turmaResponsavel = new TurmaResponsavel("Rua A", "Turma A");

        admin = new Admin("admin", "admin@email.com", "admin", "admin", UserRole.ADMIN);
        admin.adicionarTurma(turmaResponsavel);

        turmaDTORequest = new TurmaDTORequest(
                "Turma A",
                "Nome",
                "Endereço I");

        turmaDTOResponse = new TurmaDTOResponse(
                "Turma A",
                "Tutor",
                "Endereço I");

        turma = new Turma("Endereço I",
                "Turma A",
                "Tutor");
    }

    @Test
    void deveCriarNovaTurma() {
        when(turmaRepositorio.save(any(Turma.class))).thenReturn(turma);
        when(adminRepositorio.findByEmailAdmin(anyString())).thenReturn(Optional.of(admin));

        TurmaDTORequest novaTurma = new TurmaDTORequest("Endereço", "Nome", "Tutor");

        String result = turmaServices.criarNovaTurma("admin", novaTurma);
        assertEquals("Turma " + novaTurma.nome() + " foi criada com sucesso!", result);
    }

    @Test
    void deveLancarExcecaoQuandoTurmaJaExiste() {
        when(turmaRepositorio.findByNome(anyString())).thenReturn(Optional.of(turma));
        when(adminRepositorio.findByEmailAdmin(anyString())).thenReturn(Optional.of(admin));
        assertThrows(AlreadyRegisteredException.class, () -> turmaServices.criarNovaTurma("admin", turmaDTORequest));
    }

    @Test
    void deveLancarExcecaoQuandoAdminNaoExiste() {
        when(turmaRepositorio.findByNome(anyString())).thenReturn(Optional.empty());
        when(adminRepositorio.findByEmailAdmin(anyString())).thenReturn(Optional.empty());
        TurmaDTORequest novaTurma = new TurmaDTORequest("Turma B", "Tutor", "Endereço II");
        assertThrows(EntityNotFoundException.class, () -> turmaServices.criarNovaTurma("adminNotFound", novaTurma));
    }

    @Test
    void deveDeletarTurma() {
        // Arrange
        String nomeTurma = "Turma A";
        when(turmaRepositorio.findByNome(anyString())).thenReturn(Optional.of(turma));
        when(adminRepositorio.findByEmailAdmin(anyString())).thenReturn(Optional.of(admin));

        // Act
        String result = turmaServices.deletarTurma("admin@email.com", turma.getNome());

        // Assert
        assertEquals("Turma " + nomeTurma + " deletada com sucesso", result);
    }

    @Test
    void deveLancarQuandoTurmaNaoExiste() {
        String nomeTurma = "Turma A";
        String email = "admin@email.com";
        when(turmaRepositorio.findByNome(anyString())).thenReturn(Optional.empty());
        when(adminRepositorio.findByEmailAdmin(anyString())).thenReturn(Optional.of(admin));
        assertThrows(EntityNotFoundException.class,
                () -> turmaServices.deletarTurma(email, nomeTurma));
    }

    @Test
    void deveLancarExcecaoQuandoExisteAlunosVinculadosATurma() {
        String nomeTurma = "Turma A";
        String email = "admin@email.com";
        when(turmaRepositorio.findByNome(anyString())).thenReturn(Optional.of(turma));
        when(mongoTemplate.exists(any(Query.class), any(Class.class))).thenReturn(true);
        assertThrows(
                LimitQuantityException.class, () -> turmaServices.deletarTurma(
                        email,
                        nomeTurma));
    }

    @Test
    void deveListarTurmas() {

        when(turmaRepositorio.findAll()).thenReturn(List.of(
                turma,
                new Turma("Turma B", "Nome", "Tutor"),
                new Turma("Turma C", "Nome", "Tutor")));

        // Act
        List<TurmaDTOResponse> result = turmaServices.listarTurmas();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    void deveBuscarTurmaPorNome() {
        // Arrange
        String nomeTurma = "Turma A";
        TurmaService turmaService = new TurmaService(turmaRepositorio, mongoTemplate, adminRepositorio);
        when(turmaRepositorio.findByNome(nomeTurma)).thenReturn(Optional.of(turma));

        // Act
        TurmaDTOResponse actualResponse = turmaService.buscarTurmaPorNome(nomeTurma);

        // Assert
        assertEquals(turmaDTOResponse, actualResponse);
    }

    @Test
    void deveLancarExcecaoQuandoBuscarTurmaPorNomeNaoEncontrada() {
        // Arrange
        String nomeTurma = "NotFoundTurma";
        when(turmaRepositorio.findByNome(nomeTurma)).thenReturn(Optional.empty());

        // Act
        assertThrows(EntityNotFoundException.class, () -> turmaServices.buscarTurmaPorNome(nomeTurma));
    }

    @Test
    void deveLancarExcecaoQuandoTurmaNaoExiste() {
        // Arrange
        String nomeTurma = "Turma A";
        TurmaService turmaService = new TurmaService(turmaRepositorio, mongoTemplate, adminRepositorio);
        when(turmaRepositorio.findByNome(nomeTurma)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> turmaService.buscarTurmaPorNome(nomeTurma));
    }

    @Test
    void deveListarAlunosDaTurma() {
        // Arrange
        String nomeTurma = "Turma A";

        // Mock the mongoTemplate and turmaRepositorio dependencies
        when(mongoTemplate.aggregate(any(Aggregation.class), eq("turmas"), eq(TurmaAlunoDTOResponse.class))).thenReturn(
                new AggregationResults<TurmaAlunoDTOResponse>(
                        List.of(new TurmaAlunoDTOResponse("Nome", "RG", Genero.M, Date.from(Instant.now()))),
                        Document.parse(
                                "{ \"alunos_turmaNome\": \"Nome\", \"alunos_turma\": { \"$arrayElemAt\": [ \"$alunos_turma\", 0 ] }, \"alunos_turma_genero\": \"genero\", \"alunos_turma_dataNascimento\": \"dataNascimento\" }")));

        // Create a list of expected TurmaAlunoResponse objects
        List<TurmaAlunoDTOResponse> expectedResult = List.of(
                new TurmaAlunoDTOResponse("Nome", "RG", Genero.M, Date.from(Instant.now())));

        // Act
        List<TurmaAlunoDTOResponse> result = turmaServices.listarAlunosDaTurma(nomeTurma);

        // Assert
        assertEquals(expectedResult.get(0).getGenero(), result.get(0).getGenero());
        assertEquals(expectedResult.get(0).getNome(), result.get(0).getNome());
        assertEquals(expectedResult.get(0).getRg(), result.get(0).getRg());
    }

}
