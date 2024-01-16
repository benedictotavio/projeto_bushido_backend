package br.org.institutobushido.dtos.aluno.objects.graduacao;

import java.util.List;

import br.org.institutobushido.model.aluno.object.Faltas;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record GraduacaoDTOResponse(int kyu, List<Faltas> faltas, boolean status, int frequencia) {
}
