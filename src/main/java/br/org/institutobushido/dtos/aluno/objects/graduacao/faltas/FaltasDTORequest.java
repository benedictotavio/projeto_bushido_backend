package br.org.institutobushido.dtos.aluno.objects.graduacao.faltas;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record FaltasDTORequest(
        @NotNull(message = "Adicione um motivo da falta") String motivo,
        @NotNull(message = "Adicione a data da falta") Date data,
        String observacao) {
}