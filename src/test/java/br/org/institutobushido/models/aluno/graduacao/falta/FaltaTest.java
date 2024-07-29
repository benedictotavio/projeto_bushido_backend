package br.org.institutobushido.models.aluno.graduacao.falta;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.institutobushido.providers.utils.resources.exceptions.LimitQuantityException;

@SpringBootTest
class FaltaTest {
    private final Date DATA_FUTURA = new Date(new Date().getTime() + 2000 * 60 * 60 * 24 * 4);

    @Test
    void test_valid_parameters() {
        assertThrows(LimitQuantityException.class,
                () -> new Falta("motivo", "observacao", DATA_FUTURA));
    }
}
