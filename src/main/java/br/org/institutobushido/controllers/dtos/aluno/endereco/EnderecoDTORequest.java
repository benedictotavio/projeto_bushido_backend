package br.org.institutobushido.controllers.dtos.aluno.endereco;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record EnderecoDTORequest(
        String cidade,
        String estado,
        @NotNull(message = "Cep é obrigatório") @Pattern(regexp = "^\\d{8}$", message = "Cep inválido") String cep,
        @NotNull(message = "Numero é obrigatório") String numero) {
}
