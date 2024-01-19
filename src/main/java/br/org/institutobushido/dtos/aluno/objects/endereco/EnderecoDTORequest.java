package br.org.institutobushido.dtos.aluno.objects.endereco;

import com.mongodb.lang.NonNull;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record EnderecoDTORequest(
    @NonNull()
    String cidade,
    @NonNull()
    String estado,
    @NonNull()
    String cep,
    @NonNull()
    String numero) {
}
