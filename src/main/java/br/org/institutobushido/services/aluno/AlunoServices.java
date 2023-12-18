package br.org.institutobushido.services.aluno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.institutobushido.model.aluno.Aluno;
import br.org.institutobushido.repositories.AlunoRepositorio;

@Service
public class AlunoServices implements AlunoServicesInterface {
    
    @Autowired
    private AlunoRepositorio alunoRepositorio;

    public Aluno adicionarAluno(Aluno aluno) {
        return alunoRepositorio.save(aluno);
    }
}
