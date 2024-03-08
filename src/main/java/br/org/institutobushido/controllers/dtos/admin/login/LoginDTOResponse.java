package br.org.institutobushido.controllers.dtos.admin.login;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record LoginDTOResponse(String token) {
}
