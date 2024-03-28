package br.org.institutobushido.mappers;

import java.util.List;
import java.util.stream.Collectors;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.GraduacaoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.GraduacaoDTOResponse;
import br.org.institutobushido.model.aluno.graduacao.Graduacao;

public class GraduacaoMapper {

    private GraduacaoMapper() {
    }

    public static List<GraduacaoDTOResponse> mapToListGraduacaoDTOResponse(List<Graduacao> graduacoes) {
        return graduacoes.stream().map(GraduacaoMapper::mapToGraduacaoDTOResponse).collect(Collectors.toList());
    }

    public static Graduacao mapToGraduacao(GraduacaoDTORequest graduacaoDTORequest) {
        if (graduacaoDTORequest == null) {
            return null;
        }
        return new Graduacao(graduacaoDTORequest.kyu());
    }

    public static GraduacaoDTOResponse mapToGraduacaoDTOResponse(Graduacao graduacao) {
        if (graduacao == null) {
            return null;
        }
        return GraduacaoDTOResponse.builder()
                .withFrequencia(graduacao.getFrequencia())
                .withKyu(graduacao.getKyu())
                .withDan(graduacao.getDan())
                .withFaltas(graduacao.getFaltas())
                .withStatus(graduacao.isStatus())
                .withAprovado(graduacao.isAprovado())
                .withCargaHoraria(graduacao.getCargaHoraria())
                .withFimGraduacao(graduacao.getFimGraduacao())
                .withInicioGraduacao(graduacao.getInicioGraduacao())
                .build();
    }
}
