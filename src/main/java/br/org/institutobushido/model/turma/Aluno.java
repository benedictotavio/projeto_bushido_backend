package br.org.institutobushido.model.turma;

import java.util.Date;

import br.org.institutobushido.enums.aluno.Genero;
import lombok.Getter;

@Getter
public class Aluno {
    private String nome;
    private Date dataNascimento;
    private Genero genero;
    private String rg;
    private Date dataPreenchimento;

    public Aluno(String rg, String nome, Date dataNascimento, Genero genero) {
        this.rg = rg;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.dataPreenchimento = new Date();
    }
}
