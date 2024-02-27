package br.org.institutobushido.services.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.mongodb.MongoException;
import br.org.institutobushido.dtos.admin.signup.SignUpDTORequest;
import br.org.institutobushido.dtos.admin.signup.SignUpDTOResponse;
import br.org.institutobushido.model.admin.Admin;
import br.org.institutobushido.repositories.AdminRepositorio;
@Service
public class AdminServices implements AdminServiceInterface, UserDetailsService {

    @Autowired
    private AdminRepositorio adminRepositorio;

    @Override
    public SignUpDTOResponse signup(SignUpDTORequest adminDTORequest) {
        UserDetails adminEncontrado = adminRepositorio.findByEmail(adminDTORequest.email());

        if (adminEncontrado != null) {
            throw new MongoException("O Administrador com o rg " + adminDTORequest.email() + " ja esta cadastrado!");
        }

        Admin admin = new Admin();
        admin.setNome(adminDTORequest.nome());
        admin.setEmail(adminDTORequest.email());
        admin.setSenha(new BCryptPasswordEncoder().encode(adminDTORequest.senha()));
        admin.setCargo(adminDTORequest.cargo());
        admin.setRole(adminDTORequest.role());

        Admin novoAdmin = adminRepositorio.save(admin);

        return SignUpDTOResponse.builder()
                .withEmail(novoAdmin.getEmail())
                .withNome(novoAdmin.getNome())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails adminEncontrado = adminRepositorio.findByEmail(username);
        if (adminEncontrado == null) {
            throw new UsernameNotFoundException("O Administrador com o email " + username + " nao foi encontrado!");
        }
        return adminEncontrado;
    }

}
