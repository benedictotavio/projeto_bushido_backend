package br.org.institutobushido.controllers.dtos.aluno.graduacao;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record GraduacaoDTORequest(
                @NotNull(message = "Kyu é obrigatório") @Min(1) @Max(7) int kyu,
                @Min(1) int dan) {
}
