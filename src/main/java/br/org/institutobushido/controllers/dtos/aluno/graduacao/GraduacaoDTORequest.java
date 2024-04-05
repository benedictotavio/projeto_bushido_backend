package br.org.institutobushido.controllers.dtos.aluno.graduacao;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record GraduacaoDTORequest(
        @Min(1) @Max(7) @NotNull(message = "Kyu é obrigatório") int kyu) {
}
