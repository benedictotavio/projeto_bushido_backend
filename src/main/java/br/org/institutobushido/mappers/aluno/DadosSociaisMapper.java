package br.org.institutobushido.mappers.aluno;

import br.org.institutobushido.controllers.dtos.aluno.dados_sociais.DadosSociaisDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.dados_sociais.DadosSociaisDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.dados_sociais.UpdateDadosSociaisDTORequest;
import br.org.institutobushido.model.aluno.Aluno;
import br.org.institutobushido.model.aluno.dados_sociais.DadosSociais;

public class DadosSociaisMapper {
    private DadosSociaisMapper() {
    }

    public static DadosSociais setDadosSociais(UpdateDadosSociaisDTORequest dadosSociaisDTORequest, Aluno aluno) {
        if (dadosSociaisDTORequest == null) {
            return null;
        }

        aluno.getDadosSociais().setImovel(dadosSociaisDTORequest.imovel());
        aluno.getDadosSociais().setBolsaFamilia(dadosSociaisDTORequest.bolsaFamilia());
        aluno.getDadosSociais().setAuxilioBrasil(dadosSociaisDTORequest.auxilioBrasil());
        aluno.getDadosSociais()
                .setAlunoContribuiParaRenda(dadosSociaisDTORequest.alunoContribuiParaRenda());
        aluno.getDadosSociais()
                .setContribuintesDaRendaFamiliar(dadosSociaisDTORequest.contribuintesDaRendaFamiliar());
        aluno.getDadosSociais()
                .setNumerosDePessoasNaCasa(dadosSociaisDTORequest.numerosDePessoasNaCasa());
        aluno.getDadosSociais().setRendaFamiliarEmSalariosMinimos(
                dadosSociaisDTORequest.rendaFamiliarEmSalariosMinimos());

        return aluno.getDadosSociais();
    }

    public static DadosSociais mapToDadosSociais(DadosSociaisDTORequest dadosSociaisDTORequest) {

        if (dadosSociaisDTORequest == null) {
            return null;
        }

        DadosSociais dadosSociais = new DadosSociais();
        dadosSociais.setAlunoContribuiParaRenda(dadosSociaisDTORequest.alunoContribuiParaRenda());
        dadosSociais.setAuxilioBrasil(dadosSociaisDTORequest.auxilioBrasil());
        dadosSociais.setBolsaFamilia(dadosSociaisDTORequest.bolsaFamilia());
        dadosSociais.setContribuintesDaRendaFamiliar(dadosSociaisDTORequest.contribuintesDaRendaFamiliar());
        dadosSociais.setImovel(dadosSociaisDTORequest.imovel());
        dadosSociais.setNumerosDePessoasNaCasa(dadosSociaisDTORequest.numerosDePessoasNaCasa());
        dadosSociais.setRendaFamiliarEmSalariosMinimos(dadosSociaisDTORequest.rendaFamiliarEmSalariosMinimos());

        return dadosSociais;
    }

    public static DadosSociais mapToDadosSociais(UpdateDadosSociaisDTORequest dadosEditadosSociaisDTORequest) {

        if (dadosEditadosSociaisDTORequest == null) {
            return null;
        }

        DadosSociais dadosSociais = new DadosSociais();
        dadosSociais.setAlunoContribuiParaRenda(dadosEditadosSociaisDTORequest.alunoContribuiParaRenda());
        dadosSociais.setAuxilioBrasil(dadosEditadosSociaisDTORequest.auxilioBrasil());
        dadosSociais.setBolsaFamilia(dadosEditadosSociaisDTORequest.bolsaFamilia());
        dadosSociais.setContribuintesDaRendaFamiliar(dadosEditadosSociaisDTORequest.contribuintesDaRendaFamiliar());
        dadosSociais.setImovel(dadosEditadosSociaisDTORequest.imovel());
        dadosSociais.setNumerosDePessoasNaCasa(dadosEditadosSociaisDTORequest.numerosDePessoasNaCasa());
        dadosSociais.setRendaFamiliarEmSalariosMinimos(dadosEditadosSociaisDTORequest.rendaFamiliarEmSalariosMinimos());

        return dadosSociais;
    }

    public static DadosSociaisDTOResponse mapToDadosSociaisDTOResponse(DadosSociais dadosSociais) {
        if (dadosSociais == null) {
            return null;
        }
        return DadosSociaisDTOResponse.builder().withAlunoContribuiParaRenda(dadosSociais.isAlunoContribuiParaRenda())
                .withAuxilioBrasil(dadosSociais.isAuxilioBrasil()).withBolsaFamilia(dadosSociais.isBolsaFamilia())
                .withContribuintesDaRendaFamiliar(dadosSociais.getContribuintesDaRendaFamiliar())
                .withImovel(dadosSociais.getImovel())
                .withNumerosDePessoasNaCasa(dadosSociais.getNumerosDePessoasNaCasa())
                .withRendaFamiliarEmSalariosMinimos(dadosSociais.getRendaFamiliarEmSalariosMinimos()).build();
    }

    public static UpdateDadosSociaisDTORequest mapToUpdateDadosSociaisDTORequest(DadosSociais dadosSociais) {
        if (dadosSociais == null) {
            return null;
        }

        return UpdateDadosSociaisDTORequest.builder()
                .withAlunoContribuiParaRenda(dadosSociais.isAlunoContribuiParaRenda())
                .withAuxilioBrasil(dadosSociais.isAuxilioBrasil()).withBolsaFamilia(dadosSociais.isBolsaFamilia())
                .withContribuintesDaRendaFamiliar(dadosSociais.getContribuintesDaRendaFamiliar())
                .withImovel(dadosSociais.getImovel())
                .withNumerosDePessoasNaCasa(dadosSociais.getNumerosDePessoasNaCasa())
                .withRendaFamiliarEmSalariosMinimos(dadosSociais.getRendaFamiliarEmSalariosMinimos()).build();
    }
}
