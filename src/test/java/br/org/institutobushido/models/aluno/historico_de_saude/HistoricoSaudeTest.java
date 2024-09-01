package br.org.institutobushido.models.aluno.historico_de_saude;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.institutobushido.models.aluno.historico_de_saude.informacoes_saude.Alergia;
import br.org.institutobushido.models.aluno.historico_de_saude.informacoes_saude.Cirurgia;
import br.org.institutobushido.models.aluno.historico_de_saude.informacoes_saude.DoencaCronica;
import br.org.institutobushido.models.aluno.historico_de_saude.informacoes_saude.UsoMedicamentoContinuo;
import br.org.institutobushido.providers.enums.aluno.TipoSanguineo;
import br.org.institutobushido.providers.utils.resources.exceptions.AlreadyRegisteredException;
import br.org.institutobushido.providers.utils.resources.exceptions.EntityNotFoundException;

@SpringBootTest
class HistoricoSaudeTest {
    private HistoricoSaude historicoSaude;

    @BeforeEach
    void setUp() {
        historicoSaude = new HistoricoSaude();
    }

    @Test
    void deveAdicionarDeficiencia() {
        String deficiencia = "Deficiency1";

        String result = historicoSaude.adiconarDeficiencia(deficiencia);

        assertTrue(historicoSaude.getDeficiencias().contains(deficiencia));
        assertEquals(deficiencia, result);
    }

    @Test
    void deveLancarExcecaoAoSeDeficienciaJaExistente() {
        String deficiencia = "Deficiency1";

        historicoSaude.adiconarDeficiencia(deficiencia);

        assertThrows(AlreadyRegisteredException.class,
                () -> historicoSaude.adiconarDeficiencia(deficiencia));
    }

    @Test
    void deverRemoverDeficiencia() {
        String deficiencia = "Deficiency1";

        historicoSaude.adiconarDeficiencia(deficiencia);

        assertEquals(deficiencia, historicoSaude.removerDeficiencia(deficiencia));
    }

    @Test
    void deverLancarExcecaoAoSeTentarRemoverDeficienciaInexistente() {
        String deficiencia = "Deficiency1";

        assertThrows(EntityNotFoundException.class,
                () -> historicoSaude.removerDeficiencia(deficiencia));
    }

    @Test
    void deveAdicionarAcompanhamento() {
        String acompanhamento = "Acompanhamento 1";

        String result = historicoSaude.adicionarAcompanhamento(acompanhamento);

        assertTrue(historicoSaude.getAcompanhamentoSaude().contains(acompanhamento));
        assertEquals(acompanhamento, result);
    }

    @Test
    void deveRetornarExcecaoSeAcompanhamentoJaExistir() {
        String acompanhamento = "Acompanhamento";

        historicoSaude.adicionarAcompanhamento(acompanhamento);

        assertThrows(AlreadyRegisteredException.class,
                () -> historicoSaude.adicionarAcompanhamento(acompanhamento));
    }

    @Test
    void deveRemoverAcompanhamento() {
        String acompanhamento = "Acompanhamento 1";

        historicoSaude.adicionarAcompanhamento(acompanhamento);
        String result = historicoSaude.removerAcompanhamento(acompanhamento);

        assertFalse(historicoSaude.getAcompanhamentoSaude().contains(acompanhamento));
        assertEquals(acompanhamento, result);
    }

    @Test
    void deveRetornarExcecaoSeAcompanhamentoNãoExistir() {
        assertThrows(AlreadyRegisteredException.class,
                () -> historicoSaude.removerAcompanhamento("acompanhamento"));
    }

    @Test
    void deveManterValoresSeForemMudadosComoNulo() {

        String medicamentoContinuo = "med1";
        String alergia = "ale1";
        String doencaCronica = "doe1";
        String cirurgia = "cir1";

        historicoSaude = new HistoricoSaude(
                TipoSanguineo.AB_POSITIVO, new UsoMedicamentoContinuo(medicamentoContinuo),
                new DoencaCronica(doencaCronica),
                new Alergia(alergia), new Cirurgia(cirurgia),
                List.of(
                        "def1", "def2", "def3"),
                List.of(
                        "aco1", "aco2", "aco3"));

        historicoSaude.setAlergia(
                null);

        historicoSaude.setCirurgia(null);
        historicoSaude.setDoencaCronica(null);
        historicoSaude.setTipoSanguineo(null);
        historicoSaude.setUsoMedicamentoContinuo(null);
        historicoSaude.setAlergia(null);

        assertEquals(cirurgia, historicoSaude.getCirurgia().getTipo());
        assertEquals(doencaCronica, historicoSaude.getDoencaCronica().getTipo());
        assertEquals(medicamentoContinuo, historicoSaude.getUsoMedicamentoContinuo().getTipo());
        assertEquals(alergia, historicoSaude.getAlergia().getTipo());
    }
}
