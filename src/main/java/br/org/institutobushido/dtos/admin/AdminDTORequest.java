package br.org.institutobushido.dtos.admin;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record AdminDTORequest(String nome, String email, String senha, String cargo) {
}