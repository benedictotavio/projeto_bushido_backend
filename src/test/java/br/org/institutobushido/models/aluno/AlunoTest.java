package br.org.institutobushido.models.aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.institutobushido.enums.aluno.FiliacaoResposavel;
import br.org.institutobushido.enums.aluno.Genero;
import br.org.institutobushido.models.aluno.responsaveis.Responsavel;
import br.org.institutobushido.resources.exceptions.AlreadyRegisteredException;
import br.org.institutobushido.resources.exceptions.EntityNotFoundException;
import br.org.institutobushido.resources.exceptions.LimitQuantityException;

@SpringBootTest
class AlunoTest {
    private Aluno aluno;

    @BeforeEach
    void setUp() {
        aluno = new Aluno("123456789", "John Doe", Date.from(Instant.now().truncatedTo(ChronoUnit.DAYS)), Genero.M);
    }

    @Test
    void deveAdicionarUmResponsavel() {
        aluno.adicionarResponsavel(
                new Responsavel("Nome", "12345678901", "Email", "Telefone", FiliacaoResposavel.OUTRO));

        assertEquals(1, aluno.getResponsaveis().size());
    }

    @Test
    void deveAdicionarUmResponsavelComFiliacaoOutro() {
        aluno.adicionarResponsavel(
                new Responsavel("Nome", "12345678901", "Email", "Telefone", FiliacaoResposavel.OUTRO));

                Responsavel responsavel = new Responsavel("Nome 2", "12345678910", "Email 2", "Telefone 2", FiliacaoResposavel.OUTRO);

        aluno.adicionarResponsavel(responsavel);
        assertEquals(2, aluno.getResponsaveis().size());
    }

    @Test
    void deveLancarUmaExcecaoAoAdicionarUmResponsavelComUmaFiliacaoJaCadastrada() {
        aluno.adicionarResponsavel(
                new Responsavel("Nome", "12345678901", "Email", "Telefone", FiliacaoResposavel.PAI));

                Responsavel responsavel = new Responsavel("Nome 2", "12345678910", "Email 2", "Telefone 2", FiliacaoResposavel.PAI);

        assertThrows(AlreadyRegisteredException.class, () -> aluno.adicionarResponsavel(responsavel));
    }

    @Test
    void deveLancarUmaExcecaoSeTiverMaisDe5Responsaveis() {
        aluno.adicionarResponsavel(new Responsavel("Nome", "12345678901", "telefone", "email", FiliacaoResposavel.OUTRO));
        aluno.adicionarResponsavel(new Responsavel("Nome", "12345678902", "telefone", "email", FiliacaoResposavel.OUTRO));
        aluno.adicionarResponsavel(new Responsavel("Nome", "12345678903", "telefone", "email", FiliacaoResposavel.OUTRO));
        aluno.adicionarResponsavel(new Responsavel("Nome", "12345678904", "telefone", "email", FiliacaoResposavel.OUTRO));
        aluno.adicionarResponsavel(new Responsavel("Nome", "12345678905", "telefone", "email", FiliacaoResposavel.OUTRO));

        Responsavel responsavel = new Responsavel("Nome", "12345678906", "telefone", "email", FiliacaoResposavel.OUTRO);

        assertThrows(LimitQuantityException.class,
            () -> aluno.adicionarResponsavel(responsavel));
    }

    @Test
    void deveRemoverUmResponsavel() {
        // Arrange
        aluno.adicionarResponsavel(
                new Responsavel("Nome", "12345678901", "Email", "Telefone", FiliacaoResposavel.OUTRO));

        String cpf = "12345678900";
        Responsavel responsavel = new Responsavel("Nome", cpf, "Email", "Telefone", FiliacaoResposavel.OUTRO);

        aluno.getResponsaveis().add(responsavel);

        // Act
        String removedCpf = aluno.removerResponsavel(cpf);

        // Assert
        assertEquals(cpf, removedCpf);
        assertEquals(1, aluno.getResponsaveis().size());
    }
    @Test
    void deveLancarUmaExcecaoAoRemoverUmResponsavelInexistente() {
        aluno.adicionarResponsavel(
                new Responsavel("Nome", "12345678901", "Email", "Telefone", FiliacaoResposavel.OUTRO));

        String cpf = "12345678900";
        Responsavel responsavel = new Responsavel("Nome", cpf, "Email", "Telefone", FiliacaoResposavel.OUTRO);

        aluno.getResponsaveis().add(responsavel);

        assertThrows(EntityNotFoundException.class, () -> {
            aluno.removerResponsavel("12345678910");
        });
    }

    @Test
    void deveRetornarExcecaoSeAlunoTiverMenosDeUmResponsavel() {
        // Arrange
        aluno.adicionarResponsavel(
                new Responsavel("Nome", "12345678901", "Email", "Telefone", FiliacaoResposavel.OUTRO));

        // Act and Assert
        assertThrows(LimitQuantityException.class, () -> {
            aluno.removerResponsavel("12345678910");
        });
    }
}
