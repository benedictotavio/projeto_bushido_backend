package br.org.institutobushido.model.graduacao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.institutobushido.model.aluno.graduacao.Graduacao;
import br.org.institutobushido.model.aluno.graduacao.falta.Falta;

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
        graduacao.adicionarFaltas(new Falta("Doente", "Tem atestado", new Date()));
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
        graduacao.adicionarFaltas(new Falta("Doente", "Tem atestado", new Date()));
        graduacao.adicionarFaltas(new Falta("Doente", "Tem atestado", new Date()));
        graduacao.adicionarFaltas(new Falta("Doente", "Tem atestado", new Date()));
        graduacao.adicionarFaltas(new Falta("Doente", "Tem atestado", new Date()));
        graduacao.adicionarFaltas(new Falta("Doente", "Tem atestado", new Date()));
        graduacao.adicionarFaltas(new Falta("Doente", "Tem atestado", new Date()));
        graduacao.aprovacao();
        assertEquals(75, graduacao.getFrequencia());
    }

}
