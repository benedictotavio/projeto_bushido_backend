package br.org.institutobushido.models.aluno.dados_sociais;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.institutobushido.utils.resources.exceptions.LimitQuantityException;

@SpringBootTest
class DadosSociaisTest {

    private DadosSociais dadosSociais;

    @BeforeEach
    void setUp() {
        dadosSociais = new DadosSociais();
    }

    @Test
    void deveRetornarExcecaoSeNumeroDePessoasForMenorQueZero() {
        assertThrows(LimitQuantityException.class,
                () -> dadosSociais.setNumerosDePessoasNaCasa(-1));
    }

    @Test
    void deveRetornarExcecaoSeNumeroDeContribuintesDaRendaFamiliarForMaiorQueNumeroDePessoasNaCasa() {
        dadosSociais.setNumerosDePessoasNaCasa(1);
        assertThrows(LimitQuantityException.class,
                () -> dadosSociais.setContribuintesDaRendaFamiliar(2));
    }

    @Test
    void deveRetornarUmaExcecaoSeContribuintesForMaiorQuePessoasNaCasa() {
        assertThrows(LimitQuantityException.class,
                () -> dadosSociais = new DadosSociais(null, 0, 2, 0));
    }
}
