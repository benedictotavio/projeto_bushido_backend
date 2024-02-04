package br.org.institutobushido.dtos.aluno.objects.graduacao;

import java.util.List;

import br.org.institutobushido.model.aluno.objects.Falta;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record GraduacaoDTOResponse(int kyu, List<Falta> faltas, boolean status, int frequencia) {
}
