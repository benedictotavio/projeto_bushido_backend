package br.org.institutobushido.models.admin.turmas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TurmaResponsavelTest {

    private TurmaResponsavel turmaResponsavel;

    @BeforeEach
    public void setUp() {
        turmaResponsavel = new TurmaResponsavel("Endereco", "Nome");
    }

    @Test
    void deveAdicionarTurmaResponsavel() {
        assertEquals("Endereco", turmaResponsavel.getEndereco());
        assertEquals("Nome", turmaResponsavel.getNome());
    }
}
