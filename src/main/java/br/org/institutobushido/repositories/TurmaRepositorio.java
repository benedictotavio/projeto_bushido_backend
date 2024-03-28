package br.org.institutobushido.repositories;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.org.institutobushido.model.turma.Turma;

@Repository
public interface TurmaRepositorio extends MongoRepository<Turma, UUID> {
    
}
