package br.org.institutobushido.controllers.dtos.admin;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record AdminDTOResponse(
        String nome,
        String email,
        String role,
        String cargo) {
}
