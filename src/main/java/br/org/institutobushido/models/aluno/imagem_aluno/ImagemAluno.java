package br.org.institutobushido.models.aluno.imagem_aluno;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImagemAluno implements Serializable {
    private static final long serialVersionUID = 2405172041950251807L;

    private String tipoImagem;
    private byte[] dadosImagem;

    public void setTipoImagem(String tipoImagem) {
        if (tipoImagem == null) {
            return;
        }
        this.tipoImagem = tipoImagem;
    }

    public void setDadosImagem(byte[] dadosImagem) {
        if (dadosImagem == null) {
            return;
        }
        this.dadosImagem = dadosImagem;
    }

}
