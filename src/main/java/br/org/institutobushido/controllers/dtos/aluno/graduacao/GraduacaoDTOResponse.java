package br.org.institutobushido.controllers.dtos.aluno.graduacao;

import java.util.List;

import br.org.institutobushido.model.aluno.graduacao.falta.Falta;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record GraduacaoDTOResponse(int kyu, List<Falta> faltas, boolean status, int frequencia) {
}
