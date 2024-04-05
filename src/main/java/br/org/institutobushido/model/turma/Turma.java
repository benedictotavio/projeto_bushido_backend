package br.org.institutobushido.model.turma;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import br.org.institutobushido.resources.exceptions.AlreadyRegisteredException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
@Document(collection = "turma")
public class Turma {

    private String endereco;

    @Indexed(unique = true, background = true)
    private String nome;

    private String tutor;

    private List<Aluno> alunos;

    public Turma(String endereco, String nome, String tutor) {
        this.endereco = endereco;
        this.nome = nome;
        this.tutor = tutor;
        this.alunos = new ArrayList<>();
    }

    public void adicionarAluno(Aluno novoAluno) {
        if (novoAluno == null) {
            return;
        }

        for (Aluno aluno : alunos) {
            if (aluno.getRg().equals(novoAluno.getRg())) {
                throw new AlreadyRegisteredException("Aluno já esta cadastrado nesta turma");
            }
        }

        this.alunos.add(novoAluno);
    }

}
