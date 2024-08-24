package br.org.institutobushido.providers.mappers.aluno;
import br.org.institutobushido.controllers.dtos.aluno.imagem_aluno.ImagemAlunoDTOResponse;
import br.org.institutobushido.models.aluno.Aluno;
import br.org.institutobushido.models.aluno.imagem_aluno.ImagemAluno;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ImagemAlunoMapper {

    private ImagemAlunoMapper() {
    }

    public static ImagemAluno updateImagemAluno(MultipartFile updateImagemAluno, Aluno aluno) throws IOException {

        if (updateImagemAluno == null) {
            return null;
        }
        aluno.getImagemAluno().setTipoImagem(updateImagemAluno.getContentType());
        aluno.getImagemAluno().setDadosImagem(updateImagemAluno.getBytes());

        return aluno.getImagemAluno();
    }


    public static ImagemAluno mapToImagemAluno(MultipartFile imagemAlunoRequest) throws IOException {
        if (imagemAlunoRequest == null) {
            return null;
        }
        ImagemAluno imagemAluno = new ImagemAluno();
        imagemAluno.setTipoImagem(imagemAlunoRequest.getContentType());
        imagemAluno.setDadosImagem(imagemAlunoRequest.getBytes());
        return imagemAluno;
    }

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