package br.org.institutobushido.providers.mappers.aluno;

import br.org.institutobushido.controllers.dtos.aluno.dados_sociais.DadosSociaisDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.dados_sociais.DadosSociaisDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.dados_sociais.UpdateDadosSociaisDTORequest;
import br.org.institutobushido.models.aluno.Aluno;
import br.org.institutobushido.models.aluno.dados_sociais.DadosSociais;

public class DadosSociaisMapper {
    private DadosSociaisMapper() {
    }

    public static DadosSociais updateDadosSociais(UpdateDadosSociaisDTORequest dadosSociaisDTORequest, Aluno aluno) {
        if (dadosSociaisDTORequest == null) {
            return null;
        }

        aluno.getDadosSociais().setImovel(dadosSociaisDTORequest.imovel());
        aluno.getDadosSociais().setBolsaFamilia(dadosSociaisDTORequest.bolsaFamilia());
        aluno.getDadosSociais().setAuxilioBrasil(dadosSociaisDTORequest.auxilioBrasil());
        aluno.getDadosSociais()
                .setNumerosDePessoasNaCasa(dadosSociaisDTORequest.numerosDePessoasNaCasa());
        aluno.getDadosSociais()
                .setAlunoContribuiParaRenda(dadosSociaisDTORequest.alunoContribuiParaRenda());
        aluno.getDadosSociais()
                .setContribuintesDaRendaFamiliar(dadosSociaisDTORequest.contribuintesDaRendaFamiliar());
        aluno.getDadosSociais().setRendaFamiliar(
                dadosSociaisDTORequest.rendaFamiliar());

        return aluno.getDadosSociais();
    }

    public static DadosSociais mapToDadosSociais(DadosSociaisDTORequest dadosSociaisDTORequest) {

        if (dadosSociaisDTORequest == null) {
            return null;
        }

        DadosSociais dadosSociais = new DadosSociais();

        dadosSociais.setImovel(dadosSociaisDTORequest.imovel());
        dadosSociais.setBolsaFamilia(dadosSociaisDTORequest.bolsaFamilia());
        dadosSociais.setAuxilioBrasil(dadosSociaisDTORequest.auxilioBrasil());
        dadosSociais
                .setNumerosDePessoasNaCasa(dadosSociaisDTORequest.numerosDePessoasNaCasa());
        dadosSociais
                .setAlunoContribuiParaRenda(dadosSociaisDTORequest.alunoContribuiParaRenda());
        dadosSociais
                .setContribuintesDaRendaFamiliar(dadosSociaisDTORequest.contribuintesDaRendaFamiliar());
        dadosSociais.setRendaFamiliar(dadosSociaisDTORequest.rendaFamiliar());

        return dadosSociais;
    }

    public static DadosSociais mapToDadosSociais(DadosSociaisDTOResponse dadosSociaisDTOResponse) {

        if (dadosSociaisDTOResponse == null) {
            return null;
        }

        return new DadosSociais(
                dadosSociaisDTOResponse.imovel(),
                dadosSociaisDTOResponse.numerosDePessoasNaCasa(),
                dadosSociaisDTOResponse.contribuintesDaRendaFamiliar(),
                dadosSociaisDTOResponse.rendaFamiliar(),
                dadosSociaisDTOResponse.bolsaFamilia(),
                dadosSociaisDTOResponse.auxilioBrasil(),
                dadosSociaisDTOResponse.alunoContribuiParaRenda());
    }

    public static DadosSociaisDTOResponse mapToDadosSociaisDTOResponse(DadosSociais dadosSociais) {
        if (dadosSociais == null) {
            return null;
        }

        System.out.println(dadosSociais.toString());

        return new DadosSociaisDTOResponse(
                dadosSociais.isBolsaFamilia(),
                dadosSociais.isAuxilioBrasil(),
                dadosSociais.getImovel(),
                dadosSociais.getNumerosDePessoasNaCasa(),
                dadosSociais.getContribuintesDaRendaFamiliar(),
                dadosSociais.isAlunoContribuiParaRenda(),
                dadosSociais.getRendaFamiliar());
    }
}
