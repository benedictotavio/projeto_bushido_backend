package br.org.institutobushido.mappers;

import br.org.institutobushido.dtos.aluno.objects.graduacao.GraduacaoDTORequest;
import br.org.institutobushido.dtos.aluno.objects.graduacao.GraduacaoDTOResponse;
import br.org.institutobushido.model.aluno.objects.Graduacao;

public class GraduacaoMapper {

    private GraduacaoMapper() {
    }

    public static Graduacao mapToGraduacao(GraduacaoDTORequest graduacaoDTORequest) {
        if (graduacaoDTORequest == null) {
            return null;
        }
        Graduacao graduacao = new Graduacao();
        graduacao.setKyu(graduacaoDTORequest.kyu());
        graduacao.setFrequencia(graduacaoDTORequest.frequencia());
        return graduacao;
    }

    public static GraduacaoDTOResponse mapToGraduacaoDTOResponse(Graduacao graduacao) {
        if (graduacao == null) {
            return null;
        }
        return GraduacaoDTOResponse.builder().withFrequencia(graduacao.getFrequencia())
                .withKyu(graduacao.getKyu()).withFaltas(graduacao.getFaltas()).build();
    }
}
