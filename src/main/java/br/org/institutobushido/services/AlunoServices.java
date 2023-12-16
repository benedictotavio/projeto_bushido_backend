package br.org.institutobushido.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.institutobushido.model.Aluno;
import br.org.institutobushido.repositories.AlunoRepositorio;
import br.org.institutobushido.services.interfaces.AlunoServicesInterface;

@Service
public class AlunoServices implements AlunoServicesInterface {
    
    @Autowired
    private AlunoRepositorio alunoRepositorio;

    public Aluno adicionarAluno(Aluno aluno) {
        return alunoRepositorio.save(aluno);
    }
}
