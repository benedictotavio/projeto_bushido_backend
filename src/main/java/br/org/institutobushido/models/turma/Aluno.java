package br.org.institutobushido.models.turma;

import java.time.LocalDate;

import br.org.institutobushido.enums.aluno.Genero;
import lombok.Getter;

@Getter
public class Aluno {
    private String nome;
    private LocalDate dataNascimento;
    private Genero genero;
    private String rg;

    public Aluno(String rg, String nome, LocalDate dataNascimento, Genero genero) {
        this.rg = rg;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
    }
}
