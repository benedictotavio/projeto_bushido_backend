package br.org.institutobushido.models.turma;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.institutobushido.enums.aluno.Genero;
import br.org.institutobushido.resources.exceptions.AlreadyRegisteredException;
import br.org.institutobushido.resources.exceptions.EntityNotFoundException;

@SpringBootTest
class TurmaTest {
    private Turma turma;
    private Aluno aluno;

    @BeforeEach
    void setUp() {
        turma = new Turma("Endereço", "Nome", "Tutor");
        aluno = new Aluno("123456789", "John Doe", LocalDate.of(1990, 1, 1), Genero.M);
    }

    @Test
    void adcionarAluno() {

        turma.adicionarAluno(aluno);

        assertEquals(1, turma.getAlunos().size());
        assertEquals(aluno, turma.getAlunos().get(0));
    }
    @Test
    void devaLancarExcecaoQuandoAlunoRgJaEstaNaTurma() {

        turma.adicionarAluno(aluno);

        Aluno novoAluno = new Aluno("123456789", "John Doe", LocalDate.of(1990, 1, 1), Genero.M);

        assertThrows(AlreadyRegisteredException.class,
                () -> turma.adicionarAluno(novoAluno));
    }
    @Test
    void deveRemoverAluno() {
        // Arrange

        turma.adicionarAluno(aluno);
    
        // Act
        turma.removerAluno(aluno.getRg());
    
        // Assert
        assertFalse(turma.getAlunos().contains(aluno));
    }

    @Test
    void devaLancarExcecaoQuandoNaoEstaNaTurma() {

        turma.adicionarAluno(aluno);

        assertThrows(EntityNotFoundException.class,
                () -> turma.removerAluno("notfoundrg"));
    }
}
