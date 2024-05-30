package br.org.institutobushido.controllers.dtos.aluno.imagem_aluno;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record ImagemAlunoDTORequest(

        String tipoImagem,
        byte[] dadosImagem
) { }
