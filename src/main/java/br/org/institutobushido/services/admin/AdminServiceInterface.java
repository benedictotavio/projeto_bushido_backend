package br.org.institutobushido.services.admin;

import br.org.institutobushido.dtos.admin.AdminDTORequest;
import br.org.institutobushido.dtos.admin.AdminDTOResponse;

public interface AdminServiceInterface {
    AdminDTOResponse signup(AdminDTORequest adminDTORequest);
}
