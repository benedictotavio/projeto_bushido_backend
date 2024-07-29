package br.org.institutobushido.providers.mappers.aluno;

import br.org.institutobushido.controllers.dtos.aluno.endereco.EnderecoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.endereco.EnderecoDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.endereco.UpdateEnderecoDTORequest;
import br.org.institutobushido.models.aluno.Aluno;
import br.org.institutobushido.models.aluno.endereco.Endereco;

public class EnderecoMapper {

    private EnderecoMapper() {
    }

    public static Endereco updateEndereco(UpdateEnderecoDTORequest updateEnderecoDTORequest, Aluno aluno) {

        if (updateEnderecoDTORequest == null) {
            return null;
        }

        aluno.getEndereco().setCep(updateEnderecoDTORequest.cep());
        aluno.getEndereco().setCidade(updateEnderecoDTORequest.cidade());
        aluno.getEndereco().setNumero(updateEnderecoDTORequest.numero());
        aluno.getEndereco().setEstado(updateEnderecoDTORequest.estado());
        aluno.getEndereco().setLogradouro(updateEnderecoDTORequest.logradouro());

        return aluno.getEndereco();
    }

    public static Endereco mapToEndereco(EnderecoDTORequest enderecoDTORequest) {
        if (enderecoDTORequest == null) {
            return null;
        }
        Endereco endereco = new Endereco();
        endereco.setCep(enderecoDTORequest.cep());
        endereco.setCidade(enderecoDTORequest.cidade());
        endereco.setEstado(enderecoDTORequest.estado());
        endereco.setNumero(enderecoDTORequest.numero());
        endereco.setLogradouro(enderecoDTORequest.logradouro());
        return endereco;
    }

    public static Endereco mapToEndereco(EnderecoDTOResponse enderecoDTOResponse) {
        if (enderecoDTOResponse == null) {
            return null;
        }
        Endereco endereco = new Endereco();
        endereco.setCep(enderecoDTOResponse.cep());
        endereco.setCidade(enderecoDTOResponse.cidade());
        endereco.setEstado(enderecoDTOResponse.estado());
        endereco.setNumero(enderecoDTOResponse.numero());
        endereco.setLogradouro(enderecoDTOResponse.logradouro());
        return endereco;
    }

    public static EnderecoDTOResponse mapToEnderecoDTOResponse(Endereco endereco) {
        if (endereco == null) {
            return null;
        }
        return EnderecoDTOResponse.builder().withCep(endereco.getCep()).withCidade(endereco.getCidade())
                .withEstado(endereco.getEstado()).withNumero(endereco.getNumero())
                .withLogradouro(endereco.getLogradouro()).build();
    }
}