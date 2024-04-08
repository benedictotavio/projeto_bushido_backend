package br.org.institutobushido.models.aluno.graduacao.falta;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.Test;

import br.org.institutobushido.resources.exceptions.LimitQuantityException;

public class FaltaTest {
    // Creating a new instance of 'Falta' with valid parameters should set the
    // 'data', 'motivo', and 'observacao' attributes correctly.
    @Test
    public void test_valid_parameters() {
        assertThrows(LimitQuantityException.class,
            () -> new Falta("motivo", "observacao", new Date(new Date().getTime() + 2000 * 60 * 60 * 24 * 4)));
    }
}
