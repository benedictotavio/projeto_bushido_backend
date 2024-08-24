package br.org.institutobushido.providers.mappers.aluno;

import br.org.institutobushido.controllers.dtos.aluno.responsavel.ResponsavelDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.responsavel.ResponsavelDTOResponse;
import br.org.institutobushido.models.aluno.responsaveis.Responsavel;
import br.org.institutobushido.providers.enums.aluno.FiliacaoResposavel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ResponsavelMapperTest {
    private Responsavel responsavel;
    private ResponsavelDTORequest responsavelDTORequest;
    private ResponsavelDTOResponse responsavelDTOResponse;

    @BeforeEach
    void setUp() {
        responsavel = new Responsavel("Nome", "12345678901", "Email", "Telefone", FiliacaoResposavel.OUTRO);
        responsavelDTORequest = new ResponsavelDTORequest("Nome", "12345678901", "Email", "Telefone", FiliacaoResposavel.OUTRO);
        responsavelDTOResponse = new ResponsavelDTOResponse("Nome", "12345678901", "Email", "Telefone", FiliacaoResposavel.OUTRO.name());
    }

    @Test
    void deveMapearParaResponsavel() {
        responsavel = ResponsavelMapper.mapToResponsavel(responsavelDTORequest);
        assertEquals(responsavel.getNome(), responsavelDTORequest.nome());
        assertEquals(responsavel.getCpf(), responsavelDTORequest.cpf());
        assertEquals(responsavel.getEmail(), responsavelDTORequest.email());
        assertEquals(responsavel.getTelefone(), responsavelDTORequest.telefone());
        assertEquals(responsavel.getFiliacao(), responsavelDTORequest.filiacao());
    }

    @Test
    void deveMapearParaResponsavelDTOResponse() {
        responsavelDTOResponse = ResponsavelMapper.mapToResponsavelDTOResponse(responsavel);
        assertEquals(responsavelDTOResponse.nome(), responsavel.getNome());
        assertEquals(responsavelDTOResponse.cpf(), responsavel.getCpf());
        assertEquals(responsavelDTOResponse.email(), responsavel.getEmail());
        assertEquals(responsavelDTOResponse.telefone(), responsavel.getTelefone());
        assertEquals(responsavelDTOResponse.filiacao(), responsavel.getFiliacao().name());
    }
}