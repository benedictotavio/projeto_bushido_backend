package br.org.institutobushido.services.admin;

import java.util.List;

import br.org.institutobushido.controllers.dtos.admin.AdminDTOResponse;
import br.org.institutobushido.controllers.dtos.admin.login.LoginDTOResponse;
import br.org.institutobushido.controllers.dtos.admin.signup.SignUpDTORequest;
import br.org.institutobushido.models.admin.Admin;

public interface AdminServiceInterface {
    void signup(SignUpDTORequest adminDTORequest);
    LoginDTOResponse login(Admin admin);
    public List<AdminDTOResponse> buscarAdminPorNome(String nome);
}
