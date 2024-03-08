package br.org.institutobushido.mappers;

import br.org.institutobushido.controllers.dtos.aluno.dados_sociais.DadosSociaisDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.dados_sociais.DadosSociaisDTOResponse;
import br.org.institutobushido.model.aluno.dados_sociais.DadosSociais;

public class DadosSociaisMapper {
    private DadosSociaisMapper() {
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
}
