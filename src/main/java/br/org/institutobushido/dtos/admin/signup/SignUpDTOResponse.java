package br.org.institutobushido.dtos.admin.signup;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record SignUpDTOResponse(String nome, String email) {
}
