package br.org.institutobushido.controllers.dtos.aluno.endereco;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record EnderecoDTOResponse(String cidade, String estado, String cep, String numero) {}
