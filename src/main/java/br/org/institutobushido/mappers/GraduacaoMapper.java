package br.org.institutobushido.mappers;

import br.org.institutobushido.controllers.dtos.aluno.graduacao.GraduacaoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.GraduacaoDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.UpdateGraduacaoDTORequest;
import br.org.institutobushido.model.aluno.Aluno;
import br.org.institutobushido.model.aluno.graduacao.Graduacao;

public class GraduacaoMapper {

    private GraduacaoMapper() {
    }

    public static Graduacao setGraduacao(UpdateGraduacaoDTORequest graduacaoDTORequest, Aluno aluno) {
        if (graduacaoDTORequest == null) {
            return null;
        }
        
        aluno.getGraduacao().setFrequencia(graduacaoDTORequest.frequencia());
        aluno.getGraduacao().setKyu(graduacaoDTORequest.kyu());

        return aluno.getGraduacao();
    }

    public static Graduacao mapToGraduacao(GraduacaoDTORequest graduacaoDTORequest) {
        if (graduacaoDTORequest == null) {
            return null;
        }
        Graduacao graduacao = new Graduacao(graduacaoDTORequest.kyu(), graduacaoDTORequest.frequencia());
        return graduacao;
    }

    public static GraduacaoDTOResponse mapToGraduacaoDTOResponse(Graduacao graduacao) {
        if (graduacao == null) {
            return null;
        }
        return GraduacaoDTOResponse.builder().withFrequencia(graduacao.getFrequencia())
                .withKyu(graduacao.getKyu()).withFaltas(graduacao.getFaltas()).withStatus(graduacao.isStatus()).build();
    }
}
