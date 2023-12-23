package br.org.institutobushido.mappers;

import br.org.institutobushido.dtos.aluno.ResponsavelDTORequest;
import br.org.institutobushido.dtos.aluno.ResponsavelDTOResponse;
import br.org.institutobushido.model.aluno.FiliacaoResposavel;
import br.org.institutobushido.model.aluno.Responsavel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ResponsavelMapperTest {

    private List<Responsavel> responsaveis = new ArrayList<>();
    private List<ResponsavelDTORequest> responsaveisDTORequest = new ArrayList<>();

    @BeforeEach
    void setup(){
        Responsavel pai = new Responsavel();
        pai.setFiliacao(FiliacaoResposavel.PAI);
        pai.setTelefone("9999999");
        pai.setCpf("987654321");
        pai.setEmail("pai@pai.com");
        pai.setNome("Pai");

        Responsavel mae = new Responsavel();
        mae.setFiliacao(FiliacaoResposavel.MAE);
        mae.setTelefone("8888888");
        mae.setCpf("44444444");
        mae.setEmail("mae@mae.com");
        mae.setNome("Mãe");

        responsaveis.add(pai);
        responsaveis.add(mae);

        ResponsavelDTORequest paiDTO = ResponsavelDTORequest.builder()
                .withFiliacao(FiliacaoResposavel.PAI)
                .withTelefone("9999999")
                .withCpf("987654321")
                .withEmail("pai@pai.com")
                .withNome("Pai")
                .build();

        ResponsavelDTORequest maeDTO = ResponsavelDTORequest.builder()
                .withFiliacao(FiliacaoResposavel.MAE)
                .withTelefone("8888888")
                .withCpf("44444444")
                .withEmail("mae@mae.com")
                .withNome("Mãe")
                .build();

        responsaveisDTORequest.add(paiDTO);
        responsaveisDTORequest.add(maeDTO);
    }

    @Test
    public void deveMapearResponsavelDTORequestParaEntidade(){
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
    public void deveMapearEntidadeParaResponsavelDTOResponse(){
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
