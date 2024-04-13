package br.org.institutobushido.models.aluno.graduacao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import br.org.institutobushido.models.aluno.graduacao.falta.Falta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.institutobushido.resources.exceptions.InactiveUserException;

@SpringBootTest
class GraduacaoTest {
    private Graduacao graduacao;

    @BeforeEach
    void setUp() {
        graduacao = new Graduacao(7, List.of(
                new Falta("doente", "Tem atestado", new Date(new Date().getTime() - 2000000000)),
                new Falta("doente", "Tem atestado", new Date(new Date().getTime() - 1700000000))
        ),
                true,
                100,
                LocalDate.now().minusMonths(3),
                LocalDate.now().plusMonths(3),
                false,
                0,
                1
        );
    }

     @Test
     void deveDefinirCargaHoraria() {
         graduacao.aprovacao();
         System.out.println("Graduacaa: " + graduacao.toString());
         assertEquals(37, graduacao.getCargaHoraria());
     }

     @Test
     void deveDefinirFrequencias() {
         graduacao.aprovacao();
         assertEquals(94, graduacao.getFrequencia());
     }

    @Test
    void deveInstaciarGraduacaoApenasComKyu() {
        Graduacao graduacao = new Graduacao(7,5);

        assertEquals(7, graduacao.getKyu());
        assertEquals(1, graduacao.getDan());
    }

    @Test
    void deveInstaciarGraduacaoComKyuEDan() {
        Graduacao graduacao = new Graduacao(1,5);
        assertEquals(1, graduacao.getKyu());
        assertEquals(5, graduacao.getDan());
    }

    @Test
    void deveInstaciarGraduacaoComKyuEDanMaiorQueZero() {
        Graduacao graduacao = new Graduacao(1,0);
        assertEquals(1, graduacao.getKyu());
        assertEquals(1, graduacao.getDan());
    }

    @Test
    void deveLancarExcecaoQuandoGraduacaoStatusForFalse() {
        Graduacao graduacao = new Graduacao(4,0);
        graduacao.setStatus(false);
        assertThrows(InactiveUserException.class, () -> graduacao.aprovacao());
    }

    @Test
    void fimGraduacaoDeveRetornarDataAtual() {
        Graduacao graduacao = new Graduacao(4,0);
        assertEquals(LocalDate.now(), graduacao.getFimGraduacao());
    }
}
