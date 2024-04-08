package br.org.institutobushido.model.aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import br.org.institutobushido.enums.aluno.Genero;

@SpringBootTest
public class AlunoTest {
    private Aluno aluno;
    @BeforeEach
    void setUp() {
        aluno = new Aluno("123456789");
    }

    @Test
    void deveDefinirGenero() {
        assertEquals(Genero.OUTRO, aluno.getGenero());
    }
}
