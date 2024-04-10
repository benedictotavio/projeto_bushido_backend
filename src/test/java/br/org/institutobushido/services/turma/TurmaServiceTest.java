package br.org.institutobushido.services.turma;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import br.org.institutobushido.controllers.dtos.turma.aluno.AlunoTurmaDTORequest;
import br.org.institutobushido.enums.aluno.Genero;
import br.org.institutobushido.controllers.dtos.turma.TurmaDTORequest;
import br.org.institutobushido.controllers.dtos.turma.TurmaDTOResponse;
import br.org.institutobushido.models.turma.Aluno;
import br.org.institutobushido.models.turma.Turma;
import br.org.institutobushido.repositories.TurmaRepositorio;

@SpringBootTest
class TurmaServiceTest {

    @Mock
    private TurmaRepositorio turmaRepositorio;

    @Mock
    private MongoTemplate mongoTemplate;

    private TurmaService turmaServices;
    private TurmaDTORequest turmaDTORequest;
    private Turma turma;

    @BeforeEach
    void setUp() {
        turmaServices = new TurmaService(turmaRepositorio, mongoTemplate);

        turmaDTORequest = new TurmaDTORequest(
                "Turma A",
                "Nome",
                "Tutor");

        turma = new Turma("Endereço I",
                "Turma A",
                "Tutor");
    }

    @Test
    void deveCriarNovaTurma() {
        // Arrange
        // Act

        when(turmaRepositorio.save(any(Turma.class))).thenReturn(turma);
        String result = turmaServices.criarNovaTurma(turmaDTORequest);
        // Assert
        assertEquals("Turma criada " + turma.getNome() + " com sucesso!", result);
    }

    // Should successfully add a new student to an existing class
    @Test
    void deveAdicionarAlunoATurma() {
        // Arrange
        when(turmaRepositorio.findByNome(turma.getNome())).thenReturn(Optional.of(turma));

        AlunoTurmaDTORequest alunoDTORequest = new AlunoTurmaDTORequest(
                "John Doe",
                LocalDate.of(1990, 1, 1),
                Genero.M,
                "1234567890");

        // Act
        String result = turmaServices.adicionarAlunoATurma(turmaDTORequest.nome(), alunoDTORequest);

        // Assert
        assertEquals("Aluno adicionado com sucesso a turma Turma A !", result);
        assertEquals(1, turma.getAlunos().size());
        assertEquals("John Doe", turma.getAlunos().get(0).getNome());
        assertEquals("1234567890", turma.getAlunos().get(0).getRg());
    }

        // It successfully removes an existing student from a class.
        @Test
        void deveRemoverAlunoDaTurma() {
            Aluno aluno = new Aluno("123456789", "John Doe", LocalDate.of(1990, 1, 1), Genero.M);

            // Arrange
            turma.adicionarAluno(aluno);
            when(turmaRepositorio.findByNome(anyString())).thenReturn(Optional.of(turma));
    
            // Act
            String result = turmaServices.removerAlunoDaTurma(turma.getNome(), aluno.getRg());
    
            // Assert
            assertEquals("Aluno removido com sucesso a turma Turma A !", result);
            assertEquals(0, turma.getAlunos().size());
        }

    @Test
    void deveDeletarTurma() {
        // Arrange
        String nomeTurma = "Turma A";
        when(turmaRepositorio.findByNome(anyString())).thenReturn(Optional.of(turma));
        // Act
        String result = turmaServices.deletarTurma(turma.getNome());

        // Assert
        assertEquals("Turma " + nomeTurma + " deletada com sucesso", result);
    }

    @Test
    void deveListarTurmas() {

        when(turmaRepositorio.findAll()).thenReturn(List.of(
            turma,
            new Turma("Turma B", "Nome", "Tutor"),
            new Turma("Turma C", "Nome", "Tutor")
        ));

        // Act
        List<TurmaDTOResponse> result = turmaServices.listarTurmas();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
    }



}
