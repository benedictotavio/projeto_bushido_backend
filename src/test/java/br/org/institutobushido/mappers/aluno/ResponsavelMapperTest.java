package br.org.institutobushido.mappers.aluno;

import br.org.institutobushido.controllers.dtos.aluno.responsavel.ResponsavelDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.responsavel.ResponsavelDTOResponse;
import br.org.institutobushido.enums.aluno.FiliacaoResposavel;
import br.org.institutobushido.models.aluno.responsaveis.Responsavel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ResponsavelMapperTest {
    private Responsavel responsavel;
    private ResponsavelDTORequest responsavelDTORequest;
    private ResponsavelDTOResponse responsavelDTOResponse;

    @BeforeEach
    public void setUp() {
        responsavel = new Responsavel("Nome", "12345678901", "Email", "Telefone", FiliacaoResposavel.OUTRO);
        responsavelDTORequest = new ResponsavelDTORequest("Nome", "12345678901", "Email", "Telefone", FiliacaoResposavel.OUTRO);
        responsavelDTOResponse = new ResponsavelDTOResponse("Nome", "12345678901", "Email", "Telefone", FiliacaoResposavel.OUTRO.name());
    }

    @Test
    public void deveMapearParaResponsavel() {
        Responsavel responsavel = ResponsavelMapper.mapToResponsavel(responsavelDTORequest);
        assertEquals(responsavel.getNome(), responsavelDTORequest.nome());
        assertEquals(responsavel.getCpf(), responsavelDTORequest.cpf());
        assertEquals(responsavel.getEmail(), responsavelDTORequest.email());
        assertEquals(responsavel.getTelefone(), responsavelDTORequest.telefone());
        assertEquals(responsavel.getFiliacao(), responsavelDTORequest.filiacao());
    }

    @Test
    public void deveMapearParaResponsavelDTOResponse() {
        responsavelDTOResponse = ResponsavelMapper.mapToResponsavelDTOResponse(responsavel);
        assertEquals(responsavelDTOResponse.nome(), responsavel.getNome());
        assertEquals(responsavelDTOResponse.cpf(), responsavel.getCpf());
        assertEquals(responsavelDTOResponse.email(), responsavel.getEmail());
        assertEquals(responsavelDTOResponse.telefone(), responsavel.getTelefone());
        assertEquals(responsavelDTOResponse.filiacao(), responsavel.getFiliacao().name());
    }
}