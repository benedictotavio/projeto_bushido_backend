package br.org.institutobushido.dtos.admin;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record AdminDTOResponse(String nome, String email) {
}
