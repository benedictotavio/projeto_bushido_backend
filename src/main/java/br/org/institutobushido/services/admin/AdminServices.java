package br.org.institutobushido.services.admin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;

import br.org.institutobushido.dtos.admin.AdminDTORequest;
import br.org.institutobushido.dtos.admin.AdminDTOResponse;
import br.org.institutobushido.model.admin.Admin;
import br.org.institutobushido.repositories.AdminRepositorio;

@Service
public class AdminServices implements AdminServiceInterface {

    @Autowired
    private AdminRepositorio adminRepositorio;

    @Override
    public AdminDTOResponse signup(AdminDTORequest adminDTORequest) {
        Optional<Admin> adminEncontrado = adminRepositorio.findByEmail(adminDTORequest.email());

        if (adminEncontrado.isPresent()) {
            throw new MongoException("O Administrador com o rg " + adminDTORequest.email() + " ja esta cadastrado!");
        }

        Admin admin = new Admin();
        admin.setNome(adminDTORequest.nome());
        admin.setEmail(adminDTORequest.email());
        admin.setSenha(adminDTORequest.senha());
        admin.setCargo(adminDTORequest.cargo());
        Admin novoAdmin = adminRepositorio.save(admin);
        return AdminDTOResponse.builder()
                .withEmail(novoAdmin.getEmail())
                .withNome(novoAdmin.getNome())
                .build();
    }

}
