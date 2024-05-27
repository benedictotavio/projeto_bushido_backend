package br.org.institutobushido.controllers.dtos.aluno.imagem_aluno;


public record UpdateImagemAlunoDTORequest(
        String tipoImagem,
        byte[] dadosImagem
) {
}
