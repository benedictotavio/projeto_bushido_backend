package br.org.institutobushido.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.org.institutobushido.models.turma.Turma;

@Repository
public interface TurmaRepositorio extends MongoRepository<Turma, String> {
    Optional<Turma> findByNome(String nome);
    void deleteByNome(String nome);
}
