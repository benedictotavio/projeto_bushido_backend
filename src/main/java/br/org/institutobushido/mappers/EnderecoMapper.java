package br.org.institutobushido.mappers;

import br.org.institutobushido.dtos.aluno.objects.endereco.EnderecoDTORequest;
import br.org.institutobushido.dtos.aluno.objects.endereco.EnderecoDTOResponse;
import br.org.institutobushido.model.aluno.object.Endereco;

public class EnderecoMapper {

    private EnderecoMapper() {}

    public static Endereco mapToEndereco(EnderecoDTORequest enderecoDTORequest) {
        if (enderecoDTORequest == null) {
            return null;
        }
        Endereco endereco = new Endereco();
        endereco.setCep(enderecoDTORequest.cep());
        endereco.setCidade(enderecoDTORequest.cidade());
        endereco.setEstado(enderecoDTORequest.estado());
        endereco.setNumero(enderecoDTORequest.numero());
        return endereco;
    }

    public static EnderecoDTOResponse mapToEnderecoDTOResponse(Endereco endereco) {
        if (endereco == null) {
            return null;
        }
        return EnderecoDTOResponse.builder().withCep(endereco.getCep()).withCidade(endereco.getCidade()).withEstado(endereco.getEstado()).withNumero(endereco.getNumero()).build();
    }
}