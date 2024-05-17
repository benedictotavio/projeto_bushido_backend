package br.org.institutobushido.mappers.aluno;

import br.org.institutobushido.controllers.dtos.aluno.dados_sociais.DadosSociaisDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.endereco.EnderecoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.imagem_aluno.ImagemAlunoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.imagem_aluno.ImagemAlunoDTOResponse;
import br.org.institutobushido.models.aluno.Aluno;
import br.org.institutobushido.models.aluno.dados_sociais.DadosSociais;
import br.org.institutobushido.models.aluno.endereco.Endereco;
import br.org.institutobushido.models.aluno.imagem_aluno.ImagemAluno;
import org.springframework.web.multipart.MultipartFile;

public class ImagemAlunoMapper {

    private ImagemAlunoMapper() {
    }

    public static ImagemAluno updateImagemAluno(MultipartFile updateImagemAlunoDTORequest, Aluno aluno) {

        if (updateImagemAlunoDTORequest == null) {
            return null;
        }
        aluno.getImagemAluno().setTipoImagem(updateImagemAlunoDTORequest.getContentType());
        aluno.getImagemAluno().setDadosImagem(ImagemAluno.converterParaStringBase64(updateImagemAlunoDTORequest));

        return aluno.getImagemAluno();
    }


    public static ImagemAluno mapToImagemAluno(MultipartFile imagemAlunoRequest){
        if (imagemAlunoRequest == null) {
            return null;
        }
        ImagemAluno imagemAluno = new ImagemAluno();
        imagemAluno.setTipoImagem(imagemAlunoRequest.getContentType());
        imagemAluno.setDadosImagem(ImagemAluno.converterParaStringBase64(imagemAlunoRequest));
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