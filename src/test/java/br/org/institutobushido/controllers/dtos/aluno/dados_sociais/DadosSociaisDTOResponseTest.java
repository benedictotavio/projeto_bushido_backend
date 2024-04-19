package br.org.institutobushido.controllers.dtos.aluno.dados_sociais;
import br.org.institutobushido.enums.aluno.Imovel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DadosSociaisDTOResponseTest {
    @Test
    void deveRetornarDadosSociaisResponse() {
        DadosSociaisDTOResponse dadosSociaisDTOResponse = DadosSociaisDTOResponse.builder()
                .withBolsaFamilia(true)
                .withAlunoContribuiParaRenda(false)
                .withAuxilioBrasil(true)
                .withImovel(Imovel.ALUGADO)
                .withRendaFamiliar(1000)
                .withNumerosDePessoasNaCasa(4)
                .withContribuintesDaRendaFamiliar(2)
                .build();

        assertNotNull(dadosSociaisDTOResponse);
        assertTrue(dadosSociaisDTOResponse.bolsaFamilia());
        assertFalse(dadosSociaisDTOResponse.alunoContribuiParaRenda());
        assertTrue(dadosSociaisDTOResponse.auxilioBrasil());
        assertEquals(Imovel.ALUGADO, dadosSociaisDTOResponse.imovel());
        assertEquals(1000, dadosSociaisDTOResponse.rendaFamiliar());
        assertEquals(4, dadosSociaisDTOResponse.numerosDePessoasNaCasa());
        assertEquals(2, dadosSociaisDTOResponse.contribuintesDaRendaFamiliar());
    }
}
