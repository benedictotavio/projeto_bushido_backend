package br.org.institutobushido.models.aluno.imagem_aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ImagemAlunoTest {
    private ImagemAluno imagemAluno;

    private byte[] dadosImagem = { Byte.MAX_VALUE };

    @BeforeEach
    void setup() {
        imagemAluno = new ImagemAluno();
    }

    @Test
    void deveSetarValoresDaImagem() {
        imagemAluno.setTipoImagem(
                "Jpg");
        imagemAluno.setDadosImagem(dadosImagem);
        assertEquals("Jpg", imagemAluno.getTipoImagem());
        assertEquals(dadosImagem, imagemAluno.getDadosImagem());
    }

    @Test
    void deveManterValorAlteradoComNulo(){

        byte[] dadosDaNovaImagem = {};
        String png = "png";

        imagemAluno = new ImagemAluno(png, dadosImagem);

        imagemAluno.setTipoImagem(null);
        imagemAluno.setDadosImagem(dadosDaNovaImagem);

        assertEquals(png, imagemAluno.getTipoImagem());
        assertEquals(dadosImagem, imagemAluno.getDadosImagem());
    }

}
