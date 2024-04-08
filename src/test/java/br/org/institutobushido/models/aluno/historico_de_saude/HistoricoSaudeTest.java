package br.org.institutobushido.models.aluno.historico_de_saude;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.org.institutobushido.resources.exceptions.AlreadyRegisteredException;

public class HistoricoSaudeTest {
    private HistoricoSaude historicoSaude;

    @BeforeEach
    void setUp() {
        historicoSaude = new HistoricoSaude();
    }
    
    @Test
    public void deveAdicionarDeficiencia() {
        String deficiencia = "Deficiency1";
    
        String result = historicoSaude.adiconarDeficiencia(deficiencia);
    
        assertTrue(historicoSaude.getDeficiencias().contains(deficiencia));
        assertEquals(deficiencia, result);
    }

    @Test
    public void deveLancarExcecaoAoSeDeficienciaJaExistente() {
        String deficiencia = "Deficiency1";
    
        historicoSaude.adiconarDeficiencia(deficiencia);
    
        assertThrows(AlreadyRegisteredException.class,
            () -> historicoSaude.adiconarDeficiencia(deficiencia));
    }
}
