package br.org.institutobushido.services.admin;

import br.org.institutobushido.controllers.dtos.admin.login.LoginDTOResponse;
import br.org.institutobushido.controllers.dtos.admin.signup.SignUpDTORequest;
import br.org.institutobushido.model.admin.Admin;

public interface AdminServiceInterface {
    void signup(SignUpDTORequest adminDTORequest);
    LoginDTOResponse login(Admin admin);
}
