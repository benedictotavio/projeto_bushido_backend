package br.org.institutobushido.model.aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import br.org.institutobushido.model.aluno.object.DadosEscolares;
import br.org.institutobushido.model.aluno.object.DadosSociais;

@SpringBootTest
class AlunoTest {
    private Aluno aluno;

    @BeforeEach
    void setUp() {
        aluno = new Aluno();
        aluno.setNome("João Lucas");
        aluno.setDadosSociais(new DadosSociais());
        aluno.setDadosEscolares(new DadosEscolares());
        aluno.setDataPreenchimento(new Date());
        aluno.setRg("123456789");
        aluno.setFaltas(2);
        aluno.setActive(true);
        ;
    }

    @Test
    void deveAdicionarFaltaAoAluno() {
        int quantidadeTotalDeFaltasAtual = aluno.getFaltas();
        aluno.adicionarFalta();
        assertEquals(quantidadeTotalDeFaltasAtual + 1, aluno.getFaltas());
    }

    @Test
    void nãoDeveRetirarFaltaAoAluno() {
        aluno.setFaltas(0);
        aluno.retiraFalta();
        assertEquals(0, aluno.getFaltas());
    }

    @Test
    void deveRetirarFaltaAoAluno() {
        aluno.setFaltas(5);
        int quantidadeTotalDeFaltasAtual = aluno.getFaltas();
        aluno.retiraFalta();
        assertEquals(quantidadeTotalDeFaltasAtual - 1, aluno.getFaltas());
    }

    @Test
    void deveAlterarStatusSeTiverMaisDeCincoFaltas() {
        // Arrange
        aluno.setFaltas(5);

        // Act
        aluno.isStatus();

        // Assert
        assertFalse(aluno.isActive());
    }
}
