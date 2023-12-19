package br.org.institutobushido.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.org.institutobushido.model.aluno.Aluno;

@Repository
public interface AlunoRepositorio extends MongoRepository<Aluno, String> {
    Optional<Aluno> findByRg(String rg);
}
