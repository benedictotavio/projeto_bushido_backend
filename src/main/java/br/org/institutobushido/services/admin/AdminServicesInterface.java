package br.org.institutobushido.services.admin;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import br.org.institutobushido.controllers.dtos.admin.AdminDTOResponse;
import br.org.institutobushido.controllers.dtos.admin.login.LoginDTOResponse;
import br.org.institutobushido.controllers.dtos.admin.signup.SignUpDTORequest;
import br.org.institutobushido.models.admin.Admin;

public interface AdminServicesInterface {
    void signup(SignUpDTORequest adminDTORequest);
    LoginDTOResponse login(Admin admin);
    List<AdminDTOResponse> buscarAdminPorNome(String nome);
    UserDetails loadUserByUsername(String username);
    String validateToken(String token);

}
