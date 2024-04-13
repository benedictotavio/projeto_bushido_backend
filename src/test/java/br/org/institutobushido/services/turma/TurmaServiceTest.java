package br.org.institutobushido.services.turma;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import br.org.institutobushido.controllers.dtos.turma.TurmaDTORequest;
import br.org.institutobushido.controllers.dtos.turma.TurmaDTOResponse;
import br.org.institutobushido.models.turma.Turma;
import br.org.institutobushido.repositories.AdminRepositorio;
import br.org.institutobushido.repositories.TurmaRepositorio;

@SpringBootTest
class TurmaServiceTest {

    @Mock
    private TurmaRepositorio turmaRepositorio;

    @Mock
    private AdminRepositorio adminRepositorio;

    @Mock
    private MongoTemplate mongoTemplate;

    private TurmaService turmaServices;
    private TurmaDTORequest turmaDTORequest;
    private Turma turma;

    @BeforeEach
    void setUp() {
        turmaServices = new TurmaService(turmaRepositorio, mongoTemplate, adminRepositorio);

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
        String result = turmaServices.criarNovaTurma("admin", turmaDTORequest);
        // Assert
        assertEquals("Turma criada " + turma.getNome() + " com sucesso!", result);
    }

    @Test
    void deveDeletarTurma() {
        // Arrange
        String nomeTurma = "Turma A";
        when(turmaRepositorio.findByNome(anyString())).thenReturn(Optional.of(turma));
        // Act
        String result = turmaServices.deletarTurma("admin", turma.getNome());

        // Assert
        assertEquals("Turma " + nomeTurma + " deletada com sucesso", result);
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

}
