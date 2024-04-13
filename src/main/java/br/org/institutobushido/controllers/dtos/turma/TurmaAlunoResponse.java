package br.org.institutobushido.controllers.dtos.turma;

import java.io.Serializable;
import java.util.Date;

import br.org.institutobushido.enums.aluno.Genero;
import lombok.Data;

@Data
public class TurmaAlunoResponse implements Serializable {
    private String nome;
    private String rg;
    private Genero genero;
    private Date dataNascimento;
}
