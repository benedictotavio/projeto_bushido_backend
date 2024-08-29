package br.org.institutobushido.providers.mappers.aluno;

import br.org.institutobushido.controllers.dtos.aluno.dados_sociais.DadosSociaisDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.dados_sociais.DadosSociaisDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.dados_sociais.UpdateDadosSociaisDTORequest;
import br.org.institutobushido.models.aluno.Aluno;
import br.org.institutobushido.models.aluno.dados_escolares.DadosEscolares;
import br.org.institutobushido.models.aluno.dados_sociais.DadosSociais;
import br.org.institutobushido.models.aluno.endereco.Endereco;
import br.org.institutobushido.providers.enums.aluno.Genero;
import br.org.institutobushido.providers.enums.aluno.Imovel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DadosSociaisMapperTest {

    private DadosSociais dadosSociais;
    private DadosSociaisDTORequest dadosSociaisDTORequest;
    private DadosSociaisDTOResponse dadosSociaisDTOResponse;
    private UpdateDadosSociaisDTORequest updateDadosSociaisDTORequest;
    private final Aluno aluno = new Aluno();

    @BeforeEach
    void setUp() {

        aluno.setNome("NOME");
        aluno.setCpf("11111111111");
        aluno.setDataNascimento(new Date().getTime());
        aluno.setGenero(Genero.M);

        aluno.setDadosEscolares(
                new DadosEscolares("ESCOLA"));

        aluno.setEndereco(
                new Endereco(
                        "CIDADE",
                        "ESTADO",
                        "CEP",
                        "100",
                        "LOGRADOURO"));

        aluno.setDadosSociais(
                new DadosSociais(Imovel.PROPRIO,
                        4,
                        2,
                        2000,
                        false,
                        false,
                        false));

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

        assertEquals(dadosSociaisDTORequest.imovel(), dadosSociais.getImovel());
        assertEquals(dadosSociaisDTORequest.numerosDePessoasNaCasa(),
                dadosSociais.getNumerosDePessoasNaCasa());
        assertEquals(dadosSociaisDTORequest.contribuintesDaRendaFamiliar(),
                dadosSociais.getContribuintesDaRendaFamiliar());
        assertEquals(dadosSociaisDTORequest.alunoContribuiParaRenda(),
                dadosSociais.isAlunoContribuiParaRenda());
        assertEquals(dadosSociaisDTORequest.rendaFamiliar(), dadosSociais.getRendaFamiliar());
        assertEquals(dadosSociaisDTORequest.auxilioBrasil(), dadosSociais.isAuxilioBrasil());
        assertEquals(dadosSociaisDTORequest.bolsaFamilia(), dadosSociais.isBolsaFamilia());
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