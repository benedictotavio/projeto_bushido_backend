package br.org.institutobushido.models.aluno.dados_sociais;

import br.org.institutobushido.providers.enums.aluno.Imovel;
import br.org.institutobushido.providers.utils.resources.exceptions.LimitQuantityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DadosSociaisTest {

    private DadosSociais dadosSociais;

    @BeforeEach
    void setUp() {
        dadosSociais = new DadosSociais();
    }

    @Test
    void deveInstaciarClasseDadosSociaisTodosOsCampos() {
        DadosSociais data = new DadosSociais(
                Imovel.PROPRIO,
                4,
                2,
                2000,
                false,
                false,
                false
        );

        assertEquals(Imovel.PROPRIO, data.getImovel());
        assertEquals(4, data.getNumerosDePessoasNaCasa());
        assertEquals(2, data.getContribuintesDaRendaFamiliar());
        assertEquals(2000, data.getRendaFamiliar());

        data.setNumerosDePessoasNaCasa(3);
        data.setContribuintesDaRendaFamiliar(3);
        data.setRendaFamiliar(3000);
        data.setImovel(Imovel.PROPRIO);
        data.setBolsaFamilia(true);
        data.setAuxilioBrasil(false);
        data.setAlunoContribuiParaRenda(false);

        assertEquals(3, data.getNumerosDePessoasNaCasa());
        assertEquals(3, data.getContribuintesDaRendaFamiliar());
        assertEquals(3000, data.getRendaFamiliar());
        assertEquals(Imovel.PROPRIO, data.getImovel());
        assertTrue(data.isBolsaFamilia());
        assertFalse(data.isAuxilioBrasil());
        assertFalse(data.isAlunoContribuiParaRenda());
    }

    @Test
    void deveInstaciarClasseDadosSociais() {
        DadosSociais data = new DadosSociais(
                Imovel.PROPRIO,
                4,
                2,
                2000
        );

        assertEquals(Imovel.PROPRIO, data.getImovel());
        assertEquals(4, data.getNumerosDePessoasNaCasa());
        assertEquals(2, data.getContribuintesDaRendaFamiliar());
        assertEquals(2000, data.getRendaFamiliar());
    }

    @Test
    void deveManterValorSeImovelForIgualANull() {
        dadosSociais.setImovel(Imovel.PROPRIO);
        dadosSociais.setImovel(null);
        assertEquals(Imovel.PROPRIO, dadosSociais.getImovel());
    }

    @Test
    void deveRetornarExcecaoSeRendaFamiliarForMenorQueUm() {
        assertThrows(LimitQuantityException.class,
                () -> new DadosSociais(
                        Imovel.PROPRIO,
                        0,
                        0,
                        200
                )
        );
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

    @Test
    void deveRetornarUmaExcecaoSeContribuintesDaRendaFamiliarForMaiorQuePessoasNaCasa() {
        assertThrows(LimitQuantityException.class,
                () -> dadosSociais = new DadosSociais(null, 2, 4, 2000));
    }
}
