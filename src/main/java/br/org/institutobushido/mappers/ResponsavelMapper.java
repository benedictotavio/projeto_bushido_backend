package br.org.institutobushido.mappers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.org.institutobushido.dtos.aluno.objects.responsavel.ResponsavelDTORequest;
import br.org.institutobushido.dtos.aluno.objects.responsavel.ResponsavelDTOResponse;
import br.org.institutobushido.model.aluno.object.Responsavel;

@Component
public class ResponsavelMapper {

    private ResponsavelMapper() {}

    public static Responsavel mapToResponsavel(ResponsavelDTORequest responsavelDTORequest) {
        if (responsavelDTORequest == null) {
            return null;
        }

        Responsavel responsavel = new Responsavel();

        responsavel.setNome(responsavelDTORequest.nome());
        responsavel.setFiliacao(responsavelDTORequest.filiacao());
        responsavel.setTelefone(responsavelDTORequest.telefone());
        responsavel.setCpf(responsavelDTORequest.cpf());
        responsavel.setEmail(responsavelDTORequest.email());

        return responsavel;
    }

    public static ResponsavelDTOResponse mapToResponsavelDTOResponse(Responsavel responsavel) {
        if (responsavel == null) {
            return null;
        }

        return ResponsavelDTOResponse.builder()
                .withNome(responsavel.getNome())
                .withCpf(responsavel.getCpf())
                .withEmail(responsavel.getEmail())
                .withTelefone(responsavel.getTelefone())
                .withFiliacao(responsavel.getFiliacao().name())
                .build();
    }

    public static List<ResponsavelDTOResponse> mapToResponsaveisDTOResponse(List<Responsavel> responsaveis) {
        if (responsaveis == null) {
            return Collections.emptyList();
        }

        return responsaveis.stream().map(ResponsavelMapper::mapToResponsavelDTOResponse)
                .collect(Collectors.toList());
    }

    public static List<Responsavel> mapToResponsaveis(List<ResponsavelDTORequest> responsaveis) {
        if (responsaveis == null) {
            return Collections.emptyList();
        }

        return responsaveis.stream().map(ResponsavelMapper::mapToResponsavel)
                .collect(Collectors.toList());
    }
}
