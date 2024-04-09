package br.org.institutobushido.mappers.aluno;

import br.org.institutobushido.controllers.dtos.aluno.graduacao.faltas.FaltaDTOResponse;
import br.org.institutobushido.models.aluno.graduacao.falta.Falta;

import java.util.List;
import java.util.stream.Collectors;

public class FaltaMapper {

    private FaltaMapper() {
    }

    public static Falta mapToFalta(FaltaDTOResponse falta) {
        if (falta == null) {
            return null;
        }
        Falta novaFalta = new Falta();
        novaFalta.setMotivo(falta.motivo());
        novaFalta.setObservacao(falta.observacao());
        novaFalta.setData(falta.data());
        return novaFalta;
    }

    public static FaltaDTOResponse mapToFaltaDTOResponse(Falta falta) {
        if (falta == null) {
            return null;
        }
        return new FaltaDTOResponse(falta.getMotivo(), falta.getObservacao(), falta.getData());
    }

    public static List<FaltaDTOResponse> mapToListFaltaDTOResponse(List<Falta> faltas) {
        return faltas.stream().map(FaltaMapper::mapToFaltaDTOResponse).collect(Collectors.toList());
    }

    public static List<Falta> mapToListFalta(List<FaltaDTOResponse> faltas) {
        return faltas.stream().map(FaltaMapper::mapToFalta).collect(Collectors.toList());
    }
}
