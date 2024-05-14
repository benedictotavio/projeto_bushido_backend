package br.org.institutobushido.mappers.aluno;

import br.org.institutobushido.controllers.dtos.aluno.imagem_aluno.ImagemAlunoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.imagem_aluno.ImagemAlunoDTOResponse;
import br.org.institutobushido.models.aluno.imagem_aluno.ImagemAluno;

import java.io.IOException;

public class ImagemAlunoMapper {

    private ImagemAlunoMapper() {
    }
/*
    public static ImagemAluno updateImagemAluno(UpdateImagemAlunoDTORequest updateImagemAlunoDTORequest, Aluno aluno) {

        if (updateImagemAlunoDTORequest == null) {
            return null;
        }

        aluno.getImagemAluno().setImagem(updateImagemAlunoDTORequest);

        return aluno.getImagemAluno();
    }

 */

    public static ImagemAluno mapToImagemAluno(ImagemAlunoDTOResponse imagemAlunoDTOResponse) {
        if (imagemAlunoDTOResponse == null) {
            return null;
        }
        ImagemAluno imagemAluno = new ImagemAluno();
        imagemAluno.setTipoImagem(imagemAlunoDTOResponse.tipoImagem());
        imagemAluno.setDadosImagem(imagemAlunoDTOResponse.dadosImagem());

        return imagemAluno;
    }

    public static ImagemAlunoDTOResponse mapToImagemAlunoDTOResponse(ImagemAluno imagemAluno) {
        if (imagemAluno == null) {
            return null;
        }
        return ImagemAlunoDTOResponse.builder()
                .withTipoImagem(imagemAluno.getTipoImagem())
                .withDadosImagem(imagemAluno.getDadosImagem()).build();
    }
}