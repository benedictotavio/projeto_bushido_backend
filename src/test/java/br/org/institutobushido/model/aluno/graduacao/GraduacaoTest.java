package br.org.institutobushido.model.aluno.graduacao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GraduacaoTest {
    private Graduacao graduacao;

    @BeforeEach
    void setUp() {
        graduacao = new Graduacao(7);
    }

    @Test
    void deveDefinirCargaHoraria() {
        graduacao.setInicioGraduacao(LocalDate.now().minusMonths(5));
        graduacao.aprovacao();
        assertEquals(63, graduacao.getCargaHoraria());
    }

    @Test
    void deveDefinirCargaHorariaComDuasFaltas() {
        graduacao.setInicioGraduacao(LocalDate.now().minusMonths(5));
        graduacao.adicionarFalta("Doente", "Tem atestado", new Date().getTime());
        graduacao.aprovacao();
        assertEquals(62, graduacao.getCargaHoraria());
    }

    @Test
    void deveDefinirFrequenciaComZeroFaltas() {
        graduacao.setInicioGraduacao(LocalDate.now().minusMonths(5));
        graduacao.aprovacao();
        assertEquals(100, graduacao.getFrequencia());
    }

    @Test
    void deveDefinirFrequenciaCom6Faltas() {
        graduacao.setInicioGraduacao(LocalDate.now().minusMonths(2));
        graduacao.adicionarFalta("Doente", "Tem atestado", new Date().getTime());
        graduacao.adicionarFalta("Doente", "Tem atestado", new Date().getTime() - 1000 * 60 * 60 * 24 * 4);
        graduacao.aprovacao();
        assertEquals(91, graduacao.getFrequencia());
    }

}
