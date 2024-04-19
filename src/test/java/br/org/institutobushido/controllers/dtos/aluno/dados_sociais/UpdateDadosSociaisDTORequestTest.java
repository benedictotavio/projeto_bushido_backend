package br.org.institutobushido.controllers.dtos.aluno.dados_sociais;

import br.org.institutobushido.enums.aluno.Imovel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UpdateDadosSociaisDTORequestTest {
    @Test
    public void deveRetornarDadosSociaisEditados() {
        UpdateDadosSociaisDTORequest updateDadosSociaisDTORequest = new UpdateDadosSociaisDTORequest(
                true,
                true,
                Imovel.ALUGADO,
                3,
                2,
                false,
                100
        );

        assertNotNull(updateDadosSociaisDTORequest);
        assertTrue(updateDadosSociaisDTORequest.bolsaFamilia());
        assertTrue(updateDadosSociaisDTORequest.auxilioBrasil());
        assertEquals(Imovel.ALUGADO, updateDadosSociaisDTORequest.imovel());
        assertEquals(3, updateDadosSociaisDTORequest.numerosDePessoasNaCasa());
        assertEquals(2, updateDadosSociaisDTORequest.contribuintesDaRendaFamiliar());
        assertFalse(updateDadosSociaisDTORequest.alunoContribuiParaRenda());
        assertEquals(100, updateDadosSociaisDTORequest.rendaFamiliar());
    }
}
