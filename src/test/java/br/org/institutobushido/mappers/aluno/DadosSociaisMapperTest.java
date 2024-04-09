package br.org.institutobushido.mappers.aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.institutobushido.controllers.dtos.aluno.dados_sociais.DadosSociaisDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.dados_sociais.DadosSociaisDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.dados_sociais.UpdateDadosSociaisDTORequest;
import br.org.institutobushido.enums.aluno.Genero;
import br.org.institutobushido.enums.aluno.Imovel;
import br.org.institutobushido.models.aluno.Aluno;
import br.org.institutobushido.models.aluno.dados_sociais.DadosSociais;

@SpringBootTest
class DadosSociaisMapperTest {

        private DadosSociais dadosSociais;
        private DadosSociaisDTORequest dadosSociaisDTORequest;
        private DadosSociaisDTOResponse dadosSociaisDTOResponse;
        private UpdateDadosSociaisDTORequest updateDadosSociaisDTORequest;

        private Aluno aluno = new Aluno("123456789", "nome", new Date(), Genero.M, "Turma A");

        @BeforeEach
        void setUp() {
                dadosSociais = new DadosSociais(
                                true, true, Imovel.ALUGADO, 3, 2, false, 100);
                dadosSociaisDTORequest = new DadosSociaisDTORequest(
                                true, true, Imovel.ALUGADO, 3, 2, false, 100);
                dadosSociaisDTOResponse = new DadosSociaisDTOResponse(
                                true, true, Imovel.ALUGADO, 3, 2, false, 100);
                updateDadosSociaisDTORequest = new UpdateDadosSociaisDTORequest(

                                true, true, Imovel.ALUGADO, 3, 2, false, 100);
        }

        @Test
        void deveRetornarUmObjetoDeDadosSociais() {
                dadosSociais = DadosSociaisMapper.mapToDadosSociais(dadosSociaisDTORequest);
                assertEquals(dadosSociaisDTORequest.auxilioBrasil(), dadosSociais.isAuxilioBrasil());
                assertEquals(dadosSociaisDTORequest.bolsaFamilia(), dadosSociais.isBolsaFamilia());
                assertEquals(dadosSociaisDTORequest.imovel(), dadosSociais.getImovel());
                assertEquals(dadosSociaisDTORequest.numerosDePessoasNaCasa(), dadosSociais.getNumerosDePessoasNaCasa());
                assertEquals(dadosSociaisDTORequest.contribuintesDaRendaFamiliar(),
                                dadosSociais.getContribuintesDaRendaFamiliar());
                assertEquals(dadosSociaisDTORequest.alunoContribuiParaRenda(),
                                dadosSociais.isAlunoContribuiParaRenda());
                assertEquals(dadosSociaisDTORequest.rendaFamiliar(), dadosSociais.getRendaFamiliar());
        }

        @Test
        void deveRetornarUmObjetoDeDadosSociaisDTOResponse() {
                dadosSociaisDTOResponse = DadosSociaisMapper.mapToDadosSociaisDTOResponse(dadosSociais);
                assertEquals(dadosSociaisDTOResponse.auxilioBrasil(), dadosSociais.isAuxilioBrasil());
                assertEquals(dadosSociaisDTOResponse.bolsaFamilia(), dadosSociais.isBolsaFamilia());
                assertEquals(dadosSociaisDTOResponse.imovel(), dadosSociais.getImovel());
                assertEquals(dadosSociaisDTOResponse.numerosDePessoasNaCasa(),
                                dadosSociais.getNumerosDePessoasNaCasa());
                assertEquals(dadosSociaisDTOResponse.contribuintesDaRendaFamiliar(),
                                dadosSociais.getContribuintesDaRendaFamiliar());
                assertEquals(dadosSociaisDTOResponse.alunoContribuiParaRenda(),
                                dadosSociais.isAlunoContribuiParaRenda());
                assertEquals(dadosSociaisDTOResponse.rendaFamiliar(), dadosSociais.getRendaFamiliar());
        }

        @Test
        void deveMapearUmObjetoDeUpdateDadosSociaisDTORequest() {
                dadosSociais = DadosSociaisMapper.updateDadosSociais(updateDadosSociaisDTORequest, aluno);

                assertEquals(updateDadosSociaisDTORequest.auxilioBrasil(), dadosSociais.isAuxilioBrasil());
                assertEquals(updateDadosSociaisDTORequest.bolsaFamilia(), dadosSociais.isBolsaFamilia());
                assertEquals(updateDadosSociaisDTORequest.imovel(), dadosSociais.getImovel());
                assertEquals(updateDadosSociaisDTORequest.numerosDePessoasNaCasa(),
                                dadosSociais.getNumerosDePessoasNaCasa());
                assertEquals(updateDadosSociaisDTORequest.contribuintesDaRendaFamiliar(),
                                dadosSociais.getContribuintesDaRendaFamiliar());
                assertEquals(updateDadosSociaisDTORequest.alunoContribuiParaRenda(),
                                dadosSociais.isAlunoContribuiParaRenda());
                assertEquals(updateDadosSociaisDTORequest.rendaFamiliar(), dadosSociais.getRendaFamiliar());
        }
}