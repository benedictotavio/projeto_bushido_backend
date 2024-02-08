package br.org.institutobushido.model.aluno;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.institutobushido.enums.Genero;
import br.org.institutobushido.model.aluno.dados_escolares.DadosEscolares;
import br.org.institutobushido.model.aluno.dados_sociais.DadosSociais;
import br.org.institutobushido.model.aluno.graduacao.Graduacao;

@SpringBootTest
class AlunoTest {
    private Aluno aluno;

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
        aluno.setGraduacao(new Graduacao());
    }
}
