package br.org.institutobushido.controllers.dtos.admin.signup;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record SignUpDTOResponse(String nome, String email) {
}
