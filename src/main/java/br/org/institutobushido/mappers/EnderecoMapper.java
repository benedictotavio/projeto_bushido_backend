package br.org.institutobushido.mappers;

import br.org.institutobushido.controllers.dtos.aluno.endereco.EnderecoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.endereco.EnderecoDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.endereco.UpdateEnderecoDTORequest;
import br.org.institutobushido.model.aluno.endereco.Endereco;

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

    public static Endereco mapToEndereco(UpdateEnderecoDTORequest enderecoEditadoDTORequest) {
        if (enderecoEditadoDTORequest == null) {
            return null;
        }
        Endereco endereco = new Endereco();
        endereco.setCep(enderecoEditadoDTORequest.cep());
        endereco.setCidade(enderecoEditadoDTORequest.cidade());
        endereco.setEstado(enderecoEditadoDTORequest.estado());
        endereco.setNumero(enderecoEditadoDTORequest.numero());
        return endereco;
    }

    public static EnderecoDTOResponse mapToEnderecoDTOResponse(Endereco endereco) {
        if (endereco == null) {
            return null;
        }
        return EnderecoDTOResponse.builder().withCep(endereco.getCep()).withCidade(endereco.getCidade()).withEstado(endereco.getEstado()).withNumero(endereco.getNumero()).build();
    }
}