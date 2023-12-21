package br.org.institutobushido.model.aluno;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Responsavel {

    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private FiliacaoResposavel filiacao;

}
