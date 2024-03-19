package br.org.institutobushido.model.aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.institutobushido.enums.aluno.Genero;
import br.org.institutobushido.model.aluno.dados_escolares.DadosEscolares;
import br.org.institutobushido.model.aluno.dados_sociais.DadosSociais;
import br.org.institutobushido.model.aluno.endereco.Endereco;
import br.org.institutobushido.model.aluno.graduacao.Graduacao;

@SpringBootTest
class AlunoTest {
    private Aluno aluno;
    private Endereco endereco;
    private Graduacao graduacao;

    @BeforeEach
    void setUp() {
        aluno = new Aluno();
        aluno.setGenero(Genero.M);
        aluno.setDataNascimento(new Date());
        aluno.setNome("João Lucas");
        aluno.setDadosSociais(new DadosSociais());
        aluno.setDadosEscolares(new DadosEscolares());
        aluno.setDataPreenchimento(new Date());
        aluno.setRg("123456789");
        aluno.setGraduacao(new Graduacao(2, 54));
        aluno.setEndereco(new Endereco("Osasuna", "Jardim Espanha", "ES", "001"));
    }

    @Test
    void deveRetornarAlterarMetodosSetsSeForInstanciado() {

        endereco = new Endereco("Cidade", "Estado", "CEP", "NUMERO");
        graduacao = new Graduacao(5, 75);
        aluno.setGraduacao(graduacao);
        aluno.setEndereco(endereco);

        assertEquals(aluno.getEndereco(), endereco);
        assertEquals(aluno.getGraduacao(), graduacao);
    }

    @Test
    void deveRetornarAlterarMetodosSetsPassadosComoNull() {
        aluno.setEndereco(null);
        aluno.setGraduacao(null);

        assertEquals(aluno.getEndereco().getCidade(), "Osasuna");
        assertEquals(aluno.getEndereco().getEstado(), "Jardim Espanha");
        assertEquals(aluno.getEndereco().getCep(), "ES");
        assertEquals(aluno.getEndereco().getNumero(), "001");
        
        assertEquals(aluno.getGraduacao().getKyu(), 2);
        assertEquals(aluno.getGraduacao().getFrequencia(), 54);
    }
}
