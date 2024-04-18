package br.org.institutobushido.controllers.dtos.turma;

import br.org.institutobushido.enums.aluno.Genero;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TurmaAlunoDTOResponseTest {
    @Test
    public void test_constructor_sets_all_attributes_correctly() {
        String nome = "John Doe";
        String rg = "123456789";
        Genero genero = Genero.M;
        Date dataNascimento = new Date();

        TurmaAlunoDTOResponse aluno = new TurmaAlunoDTOResponse(nome, rg, genero, dataNascimento);

        assertEquals(nome, aluno.getNome());
        assertEquals(rg, aluno.getRg());
        assertEquals(genero, aluno.getGenero());
        assertEquals(dataNascimento, aluno.getDataNascimento());
    }
}
