package br.org.institutobushido.models.turma;

import java.time.LocalDate;

import br.org.institutobushido.enums.aluno.Genero;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Aluno {
    private String rg;
    private String nome;
    private LocalDate dataNascimento;
    private Genero genero;
}
