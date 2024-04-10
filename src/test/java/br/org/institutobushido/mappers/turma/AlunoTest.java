package br.org.institutobushido.mappers.turma;

import br.org.institutobushido.controllers.dtos.turma.aluno.AlunoTurmaDTORequest;
import br.org.institutobushido.controllers.dtos.turma.aluno.AlunoTurmaDTOResponse;
import br.org.institutobushido.enums.aluno.Genero;
import br.org.institutobushido.models.turma.Aluno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AlunoTest {
    private Aluno aluno;

    private AlunoTurmaDTORequest alunoDTORequest;
    private AlunoTurmaDTOResponse alunoDTOResponse;



    @BeforeEach
    void setUp() {
        aluno = new Aluno("123456789", "John Doe", LocalDate.of(1990, 1, 1), Genero.M);
        alunoDTORequest = new AlunoTurmaDTORequest(
                "John Doe",
                LocalDate.of(1990, 1, 1),
                Genero.M,
                "123456789"
        );
        alunoDTOResponse = new AlunoTurmaDTOResponse(
                "123456789",
                "John Doe",
                LocalDate.of(1990, 1, 1).toString(),
                Genero.M
        );
    }

    @Test
    void deveMapearAlunoDTORequestParaAluno() {
        aluno = AlunoTurmaMapper.mapToAluno(alunoDTORequest);
        assertEquals(aluno.getRg(), alunoDTORequest.rg());
        assertEquals(aluno.getNome(), alunoDTORequest.nome());
        assertEquals(aluno.getDataNascimento(), alunoDTORequest.dataNascimento());
        assertEquals(aluno.getGenero(), alunoDTORequest.genero());
    }

    @Test
    void deveMapearAlunoParaAlunoDTOResponse() {
        alunoDTOResponse = AlunoTurmaMapper.mapToAlunoDTOResponse(aluno);
        assertEquals(alunoDTOResponse.rg(), aluno.getRg());
        assertEquals(alunoDTOResponse.nome(), aluno.getNome());
        assertEquals(alunoDTOResponse.dataNascimento(), aluno.getDataNascimento().toString());
        assertEquals(alunoDTOResponse.genero(), aluno.getGenero());
    }
}
