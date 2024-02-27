package br.org.institutobushido.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.org.institutobushido.model.admin.Admin;

@Repository
public interface AdminRepositorio extends MongoRepository<Admin, String> {
    Optional<Admin> findByEmail(String email);
}