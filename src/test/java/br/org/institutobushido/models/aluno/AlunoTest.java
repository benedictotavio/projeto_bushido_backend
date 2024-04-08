package br.org.institutobushido.models.aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.institutobushido.enums.aluno.FiliacaoResposavel;
import br.org.institutobushido.models.aluno.responsaveis.Responsavel;
import br.org.institutobushido.resources.exceptions.AlreadyRegisteredException;
import br.org.institutobushido.resources.exceptions.EntityNotFoundException;
import br.org.institutobushido.resources.exceptions.LimitQuantityException;

@SpringBootTest
class AlunoTest {
    private Aluno aluno;

    @BeforeEach
    void setUp() {
        aluno = new Aluno("123456789");
    }

    @Test
    void deveAdicionarUmResponsavel() {
        aluno.adicionarResponsavel(
                new Responsavel("Nome", "12345678901", "Email", "Telefone", FiliacaoResposavel.OUTRO));

        assertEquals(1, aluno.getResponsaveis().size());
    }

    @Test
    void deveLancarUmaExcecaoAoAdicionarUmResponsavelCpfJaCadastrado() {
        aluno.adicionarResponsavel(
                new Responsavel("Nome", "12345678901", "Email", "Telefone", FiliacaoResposavel.OUTRO));

        assertThrows(AlreadyRegisteredException.class,
                () -> aluno.adicionarResponsavel(
                        new Responsavel("Nome 2", "12345678901", "Email 2", "Telefone 2", FiliacaoResposavel.OUTRO)));
    }

    @Test
    void deveLancarUmaExcecaoAoAdicionarUmResponsavelComUmaFiliacaoJaCadastrada() {
        aluno.adicionarResponsavel(
                new Responsavel("Nome", "12345678901", "Email", "Telefone", FiliacaoResposavel.OUTRO));
        assertThrows(AlreadyRegisteredException.class,
            () -> aluno.adicionarResponsavel(
                    new Responsavel("Nome 2", "12345678901", "Email 2", "Telefone 2", FiliacaoResposavel.OUTRO))
        );
    }

        // should remove a responsavel from the list of responsaveis when given a valid cpf
        @Test
        public void test_remove_responsavel_valid_cpf() {
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

            // should throw an exception with a message "O responsavel com o cpf {cpf} nao foi encontrado" when given an invalid cpf
    @Test
    public void test_remove_responsavel_invalid_cpf() {
        // Arrange
        aluno.adicionarResponsavel(
                new Responsavel("Nome", "12345678901", "Email", "Telefone", FiliacaoResposavel.OUTRO));

                String cpf = "12345678900";
                Responsavel responsavel = new Responsavel("Nome", cpf, "Email", "Telefone", FiliacaoResposavel.OUTRO);
            
            aluno.getResponsaveis().add(responsavel);
    
        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> {
            aluno.removerResponsavel("12345678910");
        });
    }

    @Test
    public void deveRetornarExcecaoSeAlunoTiverMenosDeUmResponsavel() {
        // Arrange
        aluno.adicionarResponsavel(
                new Responsavel("Nome", "12345678901", "Email", "Telefone", FiliacaoResposavel.OUTRO));
    
        // Act and Assert
        assertThrows(LimitQuantityException.class, () -> {
            aluno.removerResponsavel("12345678910");
        });
    }
}
