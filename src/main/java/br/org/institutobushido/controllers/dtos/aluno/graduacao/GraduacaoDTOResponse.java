package br.org.institutobushido.controllers.dtos.aluno.graduacao;

import java.time.LocalDate;
import java.util.List;

import br.org.institutobushido.model.aluno.graduacao.falta.Falta;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record GraduacaoDTOResponse(
                int kyu, List<Falta> faltas, boolean status, LocalDate inicioGraduacao, LocalDate fimGraduacao,
                int frequencia, boolean aprovado, int cargaHoraria, int dan) {
}
