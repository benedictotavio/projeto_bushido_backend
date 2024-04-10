package br.org.institutobushido.controllers.dtos.aluno.graduacao;

import java.time.LocalDate;
import java.util.List;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.faltas.FaltaDTOResponse;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record GraduacaoDTOResponse(
                int kyu,
                List<FaltaDTOResponse> faltas,
                boolean status,
                LocalDate inicioGraduacao,
                LocalDate fimGraduacao,
                int frequencia,
                boolean aprovado,
                int cargaHoraria,
                int dan
) {
}
