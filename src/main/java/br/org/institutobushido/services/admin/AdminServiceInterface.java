package br.org.institutobushido.services.admin;
import br.org.institutobushido.dtos.admin.signup.SignUpDTORequest;
import br.org.institutobushido.dtos.admin.signup.SignUpDTOResponse;

public interface AdminServiceInterface {
    SignUpDTOResponse signup(SignUpDTORequest adminDTORequest);
}
