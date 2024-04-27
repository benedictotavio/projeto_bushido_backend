package br.org.institutobushido.models.aluno.graduacao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.institutobushido.models.aluno.graduacao.falta.Falta;
import br.org.institutobushido.resources.exceptions.InactiveUserException;
import br.org.institutobushido.resources.exceptions.LimitQuantityException;

@SpringBootTest
class GraduacaoTest {
    private Graduacao graduacao;

    @BeforeEach
    void setUp() {
        graduacao = new Graduacao(7, List.of(
                new Falta("doente", "Tem atestado", new Date(new Date().getTime() - 2000000000)),
                new Falta("doente", "Tem atestado", new Date(new Date().getTime() - 1700000000))),
                true,
                100,
                LocalDate.now().minusMonths(3),
                LocalDate.now().plusMonths(3),
                false,
                0,
                1, 10);
    }

    @Test
    void deveDefinirCargaHoraria() {
        graduacao.aprovacao(10);
        assertEquals(37, graduacao.getCargaHoraria());
    }

    @Test
    void deveDefinirFrequencias() {
        graduacao.aprovacao(6);
        assertEquals(94, graduacao.getFrequencia());
    }

    @Test
    void deveInstaciarGraduacaoApenasComKyu() {
        Graduacao graduacao = new Graduacao(7, 5);
        assertEquals(7, graduacao.getKyu());
        assertEquals(0, graduacao.getDan());
    }

    @Test
    void deveInstaciarGraduacaoComKyuEDan() {
        Graduacao graduacao = new Graduacao(1, 5);
        assertEquals(1, graduacao.getKyu());
        assertEquals(0, graduacao.getDan());
    }

    @Test
    void deveInstaciarGraduacaoComKyuEDanMaiorQueZero() {
        Graduacao graduacao = new Graduacao(1, 0);
        assertEquals(1, graduacao.getKyu());
        assertEquals(0, graduacao.getDan());
    }

    @Test
    void deveLancarExcecaoQuandoGraduacaoStatusForFalse() {
        Graduacao graduacao = new Graduacao(4, 0);
        graduacao.setStatus(false);
        assertThrows(InactiveUserException.class, () -> graduacao.aprovacao(8));
    }

    @Test
    void deveLancarExcecaoQuandoCargaHorariaOuInicioDaGraduaçãoForBaixo() {
        Graduacao graduacao = new Graduacao(4, 0);
        graduacao.setCargaHoraria(0);
        assertThrows(InactiveUserException.class, () -> graduacao.aprovacao(8));
    }

    @Test
    void deveLancarExcecaoQuandoNotaForMenorQue6() {
        Graduacao graduacao = new Graduacao(4, 0);
        graduacao.setStatus(false);
        assertThrows(InactiveUserException.class, () -> graduacao.aprovacao(5));
    }

    @Test
    void deveLancarExcecaoQuandoNotaForMaiorQue6() {
        Graduacao graduacao = new Graduacao(4, 0);
        assertThrows(LimitQuantityException.class, () -> graduacao.reprovacao(7));
    }

    @Test
    void fimGraduacaoDeveRetornarDataAtual() {
        Graduacao graduacao = new Graduacao(4, 0);
        assertEquals(LocalDate.now(), graduacao.getFimGraduacao());
    }

    @Test
    void deveLancarExcecaoSeQuantidadeDeFaltasForMaiorQueCargaHoraria() {
        System.out.println(graduacao.getFimGraduacao());
        graduacao.setCargaHoraria(1);
        String motivo = "Motivo";
        String observacao = "Observação";
        long data = new Date().getTime();

        assertThrows(LimitQuantityException.class, () -> graduacao.adicionarFalta(motivo, observacao, data));
    }

    @Test
    void deveGerarUmaNovaGraduacao() {
        // Arrange
        int kyu = 3;
        int dan = 0;

        // Act
        Graduacao result = Graduacao.gerarNovaGraduacao(kyu, dan);

        // Assert
        assertEquals(kyu - 1, result.getKyu());
        assertEquals(dan, result.getDan());
    }

    @Test
    void deveGerarUmaNovaGraduacaoQuandoKyuForMenorQueUm() {
        // Arrange
        int kyu = 1;
        int dan = 0;

        // Act
        Graduacao result = Graduacao.gerarNovaGraduacao(kyu, dan);

        // Assert
        assertEquals(kyu - 1, result.getKyu());
        assertEquals(dan + 1, result.getDan());
    }

    @Test
    void deveGerarUmaNovaGraduacaoQuandoKyuZero() {
        // Arrange
        int kyu = 0;
        int dan = 5;

        // Act
        Graduacao result = Graduacao.gerarNovaGraduacao(kyu, dan);

        // Assert
        assertEquals(kyu, result.getKyu());
        assertEquals(dan + 1, result.getDan());
    }
}
