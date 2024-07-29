package br.org.institutobushido.providers.mappers.admin;

import java.util.List;
import java.util.stream.Collectors;

import br.org.institutobushido.controllers.dtos.admin.AdminDTOResponse;
import br.org.institutobushido.models.admin.Admin;

public class AdminMapper {
    private AdminMapper() {}

    public static AdminDTOResponse mapToAdminDTOResponse(Admin admin) {
        return AdminDTOResponse
                .builder()
                .withNome(admin.getNome())
                .withEmail(admin.getEmail())
                .withCargo(admin.getCargo())
                .withRole(admin.getRole().toString())
                .build();
    }

    public static List<AdminDTOResponse> mapToListAdminDTOResponse(List<Admin> admins) {
        return admins.stream().map(AdminMapper::mapToAdminDTOResponse).collect(Collectors.toList());
    }
}
