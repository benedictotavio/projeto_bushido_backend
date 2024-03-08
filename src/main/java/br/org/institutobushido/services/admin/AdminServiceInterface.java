package br.org.institutobushido.services.admin;

import br.org.institutobushido.controllers.dtos.admin.login.LoginDTOResponse;
import br.org.institutobushido.controllers.dtos.admin.signup.SignUpDTORequest;
import br.org.institutobushido.controllers.dtos.admin.signup.SignUpDTOResponse;
import br.org.institutobushido.model.admin.Admin;

public interface AdminServiceInterface {
    SignUpDTOResponse signup(SignUpDTORequest adminDTORequest);
    LoginDTOResponse login(Admin admin);
}
