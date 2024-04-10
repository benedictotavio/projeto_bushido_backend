package br.org.institutobushido.models.turma;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import br.org.institutobushido.resources.exceptions.AlreadyRegisteredException;
import br.org.institutobushido.resources.exceptions.EntityNotFoundException;

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

    public boolean adicionarAluno(Aluno novoAluno) {
        for (Aluno aluno : alunos) {
            if (aluno.getRg().equals(novoAluno.getRg())) {
                throw new AlreadyRegisteredException("Aluno já esta cadastrado nesta turma");
            }
        }

        return this.alunos.add(novoAluno);
    }

    public boolean removerAluno(String rg) {

        for (Aluno aluno : alunos) {
            if (aluno.getRg().equals(rg)) {
                return alunos.remove(aluno);
                
            }
        }

        throw new EntityNotFoundException("Aluno não encontrado nesta turma");
    }

}
