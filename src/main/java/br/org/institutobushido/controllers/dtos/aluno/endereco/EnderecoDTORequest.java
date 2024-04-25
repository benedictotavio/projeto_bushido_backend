package br.org.institutobushido.controllers.dtos.aluno.endereco;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record EnderecoDTORequest(
        String cidade,
        String estado,
        @NotNull(message = "Cep é obrigatório") @Pattern(regexp = "^\\d{8}$", message = "Cep inválido") @NotEmpty(message = "Cep é obrigatório") String cep,
        @NotNull(message = "Numero é obrigatório") @NotEmpty(message = "Numero é obrigatório") String numero,
        @NotNull(message = "logradouro é obrigatório") @NotEmpty(message = "logradouro é obrigatório") String logradouro) {
}
