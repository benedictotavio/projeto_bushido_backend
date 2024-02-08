package br.org.institutobushido.dtos.aluno.endereco;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record EnderecoDTOResponse(String cidade, String estado, String cep, String numero) {}
