package br.org.institutobushido.controllers.dtos.aluno.endereco;

public record UpdateEnderecoDTORequest(
    String cidade,
    String estado,
    String cep,
    String numero
) {
}
