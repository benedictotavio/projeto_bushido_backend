package br.org.institutobushido.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.org.institutobushido.models.admin.Admin;

@Repository
public interface AdminRepositorio extends MongoRepository<Admin, String> {

    @Query(value = "{'email': ?0}")
    UserDetails findByEmail(String email);

    @Query(value = "{'email': ?0}")
    Optional<Admin> findByEmailAdmin(String email);
}