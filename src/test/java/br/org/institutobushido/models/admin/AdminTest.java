package br.org.institutobushido.models.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.institutobushido.enums.admin.UserRole;
import br.org.institutobushido.models.admin.turmas.TurmaResponsavel;

@SpringBootTest
public class AdminTest {
    private Admin admin;
    private TurmaResponsavel turmaResponsavel;

    @BeforeEach
    void setUp() {
        admin = new Admin("admin", "admin@email.com", "admin", "admin", UserRole.ADMIN);

        turmaResponsavel = new TurmaResponsavel("Tutor", "Turma A");
    }

    @Test
    void deveAdicionarTurmaResponsavel() {
        TurmaResponsavel result = admin.adicionarTurma(turmaResponsavel);

        // Assert
        assertEquals(turmaResponsavel, result);
        assertTrue(admin.getTurmas().contains(turmaResponsavel));
    }

    @Test
    void deveRemoverTurmaResponsavel() {
        admin.adicionarTurma(turmaResponsavel);
        String result = admin.removerTurma(turmaResponsavel.getNome());

        // Assert
        assertEquals(turmaResponsavel.getNome(), result);
        assertTrue(!admin.getTurmas().contains(turmaResponsavel));
    }
}
