package br.org.institutobushido.controllers.dtos.aluno.dados_sociais;

import br.org.institutobushido.enums.aluno.Imovel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DadosSociaisDTORequestTest {
    @Test
    public void deveRetornarDadosSociais() {
        DadosSociaisDTORequest dadosSociaisDTORequest = new DadosSociaisDTORequest(
                true,
                true,
                Imovel.ALUGADO,
                3,
                2,
                false,
                100
        );

        assertNotNull(dadosSociaisDTORequest);
        assertTrue(dadosSociaisDTORequest.bolsaFamilia());
        assertTrue(dadosSociaisDTORequest.auxilioBrasil());
        assertEquals(Imovel.ALUGADO, dadosSociaisDTORequest.imovel());
        assertEquals(3, dadosSociaisDTORequest.numerosDePessoasNaCasa());
        assertEquals(2, dadosSociaisDTORequest.contribuintesDaRendaFamiliar());
        assertFalse(dadosSociaisDTORequest.alunoContribuiParaRenda());
        assertEquals(100, dadosSociaisDTORequest.rendaFamiliar());
    }
}
