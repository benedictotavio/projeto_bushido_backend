package br.org.institutobushido.mappers;

import br.org.institutobushido.dtos.aluno.ResponsavelDTORequest;
import br.org.institutobushido.dtos.aluno.ResponsavelDTOResponse;
import br.org.institutobushido.enums.FiliacaoResposavel;
import br.org.institutobushido.model.aluno.Responsavel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ResponsavelMapperTest {

    @BeforeEach
    void setup(){
    }

    @Test
    void deveMapearResponsavelDTORequestParaEntidade(){
        ResponsavelDTORequest paiDTO = ResponsavelDTORequest.builder()
                .withFiliacao(FiliacaoResposavel.PAI)
                .withTelefone("9999999")
                .withCpf("987654321")
                .withEmail("pai@pai.com")
                .withNome("Pai")
                .build();

        Responsavel responsavel = ResponsavelMapper.mapToResponsavel(paiDTO);

        assertEquals(paiDTO.filiacao(), responsavel.getFiliacao());
        assertEquals(paiDTO.telefone(), responsavel.getTelefone());
        assertEquals(paiDTO.cpf(), responsavel.getCpf());
        assertEquals(paiDTO.email(), responsavel.getEmail());
        assertEquals(paiDTO.nome(), responsavel.getNome());
    }

    @Test
    void deveMapearEntidadeParaResponsavelDTOResponse(){
        Responsavel pai = new Responsavel();
        pai.setFiliacao(FiliacaoResposavel.PAI);
        pai.setTelefone("9999999");
        pai.setCpf("987654321");
        pai.setEmail("pai@pai.com");
        pai.setNome("Pai");

        ResponsavelDTOResponse responsavel = ResponsavelMapper.mapToResponsavelDTOResponse(pai);

        assertEquals(pai.getFiliacao().name(), responsavel.filiacao());
        assertEquals(pai.getTelefone(), responsavel.telefone());
        assertEquals(pai.getCpf(), responsavel.cpf());
        assertEquals(pai.getEmail(), responsavel.email());
        assertEquals(pai.getNome(), responsavel.nome());
    }

}
