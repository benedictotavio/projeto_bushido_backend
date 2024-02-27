package br.org.institutobushido.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.org.institutobushido.model.admin.Admin;

@Repository
public interface AdminRepositorio extends MongoRepository<Admin, String> {
    UserDetails findByEmail(String email);
}