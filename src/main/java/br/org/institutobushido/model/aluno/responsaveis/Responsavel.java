package br.org.institutobushido.model.aluno.responsaveis;

import java.io.Serializable;

import br.org.institutobushido.enums.aluno.FiliacaoResposavel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Responsavel implements Serializable{

    private static final long serialVersionUID = 2405172041950251807L;

    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private FiliacaoResposavel filiacao;
}