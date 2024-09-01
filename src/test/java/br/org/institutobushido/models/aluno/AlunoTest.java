package br.org.institutobushido.models.aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.institutobushido.models.aluno.graduacao.Graduacao;
import br.org.institutobushido.models.aluno.imagem_aluno.ImagemAluno;
import br.org.institutobushido.models.aluno.responsaveis.Responsavel;
import br.org.institutobushido.providers.enums.aluno.CorDePele;
import br.org.institutobushido.providers.enums.aluno.FiliacaoResposavel;
import br.org.institutobushido.providers.enums.aluno.Genero;
import br.org.institutobushido.providers.utils.resources.exceptions.AlreadyRegisteredException;
import br.org.institutobushido.providers.utils.resources.exceptions.EntityNotFoundException;
import br.org.institutobushido.providers.utils.resources.exceptions.LimitQuantityException;

@SpringBootTest
class AlunoTest {
        private Aluno aluno;

        @BeforeEach
        void setUp() {
                aluno = new Aluno();
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

                Responsavel responsavel = new Responsavel("Nome 2", "12345678910", "Email 2", "Telefone 2",
                                FiliacaoResposavel.OUTRO);

                aluno.adicionarResponsavel(responsavel);
                assertEquals(2, aluno.getResponsaveis().size());
        }

        @Test
        void deveLancarUmaExcecaoAoAdicionarUmResponsavelComUmaFiliacaoJaCadastrada() {
                aluno.adicionarResponsavel(
                                new Responsavel("Nome", "12345678901", "Email", "Telefone", FiliacaoResposavel.PAI));

                Responsavel responsavel = new Responsavel("Nome 2", "12345678910", "Email 2", "Telefone 2",
                                FiliacaoResposavel.PAI);

                assertThrows(AlreadyRegisteredException.class, () -> aluno.adicionarResponsavel(responsavel));
        }

        @Test
        void deveLancarUmaExcecaoSeTiverMaisDe5Responsaveis() {
                aluno.adicionarResponsavel(
                                new Responsavel("Nome", "12345678901", "telefone", "email", FiliacaoResposavel.OUTRO));
                aluno.adicionarResponsavel(
                                new Responsavel("Nome", "12345678902", "telefone", "email", FiliacaoResposavel.OUTRO));
                aluno.adicionarResponsavel(
                                new Responsavel("Nome", "12345678903", "telefone", "email", FiliacaoResposavel.OUTRO));
                aluno.adicionarResponsavel(
                                new Responsavel("Nome", "12345678904", "telefone", "email", FiliacaoResposavel.OUTRO));
                aluno.adicionarResponsavel(
                                new Responsavel("Nome", "12345678905", "telefone", "email", FiliacaoResposavel.OUTRO));

                Responsavel responsavel = new Responsavel("Nome", "12345678906", "telefone", "email",
                                FiliacaoResposavel.OUTRO);

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
                aluno.adicionarResponsavel(
                                new Responsavel("Nome", "12345678901", "Email", "Telefone", FiliacaoResposavel.OUTRO));

                assertThrows(LimitQuantityException.class, () -> {
                        aluno.removerResponsavel("12345678910");
                });
        }

        @Test
        void deveAdicionarUmaNovaGraduacao() {
                aluno.adicionarGraduacao(new Graduacao(7, 5));
                assertEquals(1, aluno.getGraduacao().size());
        }

        @Test
        void deveRetornarGraduacaoAtualDoAluno() {
                aluno.adicionarGraduacao(new Graduacao(7, 1));
                aluno.adicionarGraduacao(new Graduacao(5, 5));
                assertEquals(5, aluno.getGraduacaoAtual().getKyu());
        }

        @Test
        void deveRetornarIndexDoGraduacaoAtualDoAluno() {
                aluno.adicionarGraduacao(new Graduacao(7, 1));
                aluno.adicionarGraduacao(new Graduacao(5, 5));
                assertEquals(5, aluno.getGraduacao().get(aluno.getGraduacaoAtualIndex()).getKyu());
        }

        @Test
        void deveManterValoresSeSetsForemNulos() {

                byte[] dadosImagem = {
                                Byte.MAX_VALUE
                };

                ImagemAluno imagemAluno = new ImagemAluno(
                                "Jpeg",
                                dadosImagem);

                List<Responsavel> responsavels = List.of(
                                new Responsavel("Nome Test", "001112223", "323232323",
                                                "email@email.com", FiliacaoResposavel.PAI));

                // Arrange
                aluno.setCpf("000000000");
                aluno.setMatricula("123abc");
                aluno.setCartaoSus("12345678910");
                aluno.setTelefone("11009099909");
                aluno.setCorDePele(CorDePele.BRANCO);
                aluno.setResponsaveis(responsavels);
                aluno.setTurma("Turma1");
                aluno.setNome("Test 1");
                aluno.setEmail("email@email.com.br");
                aluno.setDataNascimento(
                                Instant.now().getEpochSecond() - 1000000);
                aluno.setGenero(Genero.M);
                aluno.setImagemAluno(imagemAluno);

                // Act

                aluno.setCartaoSus(null);
                aluno.setCorDePele(null);
                aluno.setCpf(null);
                aluno.setDataNascimento(0);
                aluno.setEmail(null);
                aluno.setEndereco(null);
                aluno.setGenero(null);
                aluno.setImagemAluno(null);
                aluno.setMatricula(null);
                aluno.setResponsaveis(new ArrayList<Responsavel>());

                assertEquals(imagemAluno, aluno.getImagemAluno());
                assertEquals("Test 1", aluno.getNome());
                assertEquals(new Date(Instant.now().getEpochSecond() - 1000000), aluno.getDataNascimento());
                assertEquals(Genero.M, aluno.getGenero());
                assertEquals("Turma1", aluno.getTurma());
                assertEquals("123abc", aluno.getMatricula());
                assertEquals(CorDePele.BRANCO, aluno.getCorDePele());
                assertEquals("email@email.com.br", aluno.getEmail());
                assertEquals("11009099909", aluno.getTelefone());
                assertEquals("000000000", aluno.getCpf());
                assertEquals(responsavels, aluno.getResponsaveis());
        }

        @Test
        void deveGerarUmNomeroDeMatriculaAleatorio() {
                assertNotNull(Aluno.gerarMatricula());
        }
}