package br.org.institutobushido.controllers.dtos.turma;

import java.io.Serializable;
import java.util.Date;

import br.org.institutobushido.enums.aluno.Genero;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TurmaAlunoDTOResponse implements Serializable {
    private String nome;
    private String cpf;
    private Genero genero;
    private Date dataNascimento;
}
