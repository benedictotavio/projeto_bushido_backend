package br.org.institutobushido.models.aluno.responsaveis;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.org.institutobushido.enums.aluno.FiliacaoResposavel;
import br.org.institutobushido.resources.exceptions.InvalidFormatDataException;

class ResponsavelTest {

    @Test
    void deveRetornarExcecaoSeCpfForNulo() {
        assertThrows(InvalidFormatDataException.class,
                () -> new Responsavel("nome", null, "email", "telefone", FiliacaoResposavel.OUTRO));
    }

    @Test
    void deveRetornarExcecaoSeCpfForInvalido() {
        assertThrows(InvalidFormatDataException.class, () -> new Responsavel("nome", "123", "telefone", "email", FiliacaoResposavel.OUTRO));
    }
}
