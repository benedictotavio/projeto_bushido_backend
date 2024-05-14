package br.org.institutobushido.models.aluno.imagem_aluno;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImagemAluno implements Serializable {
    private static final long serialVersionUID = 2405172041950251807L;

    private String nomeImagem;
    private String tipoImagem;
    private String DadosImagem;

    public void setTipoImagem(String tipoImagem) {
        if (tipoImagem == null) {
            return;
        }
        this.tipoImagem = tipoImagem;
    }

    public void setDadosImagem(String dadosImagem) {
        if (dadosImagem == null) {
            return;
        }
        this.DadosImagem = dadosImagem;
    }

    public static ImagemAluno converterParaBase64(MultipartFile file) {

        ImagemAluno imagemAluno = new ImagemAluno();

        try {
            byte[] byteArr = file.getBytes();
            imagemAluno.setTipoImagem(file.getContentType().split("\\/")[1]);
            imagemAluno.setDadosImagem("data:image/" + file.getContentType().split("\\/")[1] + ";base64," + Base64.getEncoder().encodeToString(byteArr));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return imagemAluno;
    }

}
