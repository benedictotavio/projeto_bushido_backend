package br.org.institutobushido.mappers.aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import br.org.institutobushido.controllers.dtos.aluno.endereco.EnderecoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.endereco.EnderecoDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.endereco.UpdateEnderecoDTORequest;
import br.org.institutobushido.enums.aluno.Genero;
import br.org.institutobushido.models.aluno.Aluno;
import br.org.institutobushido.models.aluno.endereco.Endereco;

@SpringBootTest
class EnderecoMapperTest {

    private Endereco endereco;
    private EnderecoDTORequest enderecoDTORequest;
    private EnderecoDTOResponse enderecoDTOResponse;
    private UpdateEnderecoDTORequest updateEnderecoDTORequest;
    private Aluno aluno = new Aluno("123456789", "nome", new Date(), Genero.M);

    @BeforeEach
    void setUp() {
        endereco = new Endereco();
        enderecoDTORequest = new EnderecoDTORequest(
                "CIDADE", "ESTADO", "CEP", "NUMERO");
        updateEnderecoDTORequest = new UpdateEnderecoDTORequest(
                "CIDADE", "ESTADO", "CEP", "NUMERO");
        enderecoDTOResponse = new EnderecoDTOResponse(
                "CIDADE", "ESTADO", "CEP", "NUMERO");
    }

    @Test
    void deveRetornarEnderecoDTOResponse() {
        enderecoDTOResponse = EnderecoMapper.mapToEnderecoDTOResponse(endereco);
        assertEquals(endereco.getCidade(), enderecoDTOResponse.cidade());
        assertEquals(endereco.getEstado(), enderecoDTOResponse.estado());
        assertEquals(endereco.getCep(), enderecoDTOResponse.cep());
        assertEquals(endereco.getNumero(), enderecoDTOResponse.numero());
    }

    @Test
    void deveRetornarEnderecoDeEnderecoDTORequest() {
        endereco = EnderecoMapper.mapToEndereco(enderecoDTORequest);
        assertEquals(endereco.getCidade(), endereco.getCidade());
        assertEquals(endereco.getEstado(), endereco.getEstado());
        assertEquals(endereco.getCep(), endereco.getCep());
        assertEquals(endereco.getNumero(), endereco.getNumero());
    }

    @Test
    void deveRetornarEnderecoDeUpdateEnderecoDTORequest() {
        endereco = EnderecoMapper.updateEndereco(updateEnderecoDTORequest, aluno);
        assertEquals(endereco.getCidade(), updateEnderecoDTORequest.cidade());
        assertEquals(endereco.getEstado(), updateEnderecoDTORequest.estado());
        assertEquals(endereco.getCep(), updateEnderecoDTORequest.cep());
        assertEquals(endereco.getNumero(), updateEnderecoDTORequest.numero());
    }
}
