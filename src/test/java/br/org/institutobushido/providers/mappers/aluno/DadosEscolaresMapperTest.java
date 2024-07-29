package br.org.institutobushido.providers.mappers.aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import br.org.institutobushido.controllers.dtos.aluno.dados_escolares.DadosEscolaresDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.dados_escolares.DadosEscolaresDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.dados_escolares.UpdateDadosEscolaresDTORequest;
import br.org.institutobushido.models.aluno.Aluno;
import br.org.institutobushido.models.aluno.dados_escolares.DadosEscolares;

@SpringBootTest
class DadosEscolaresMapperTest {

    private DadosEscolares dadosEscolares;
    private DadosEscolaresDTORequest dadosEscolaresDTORequest;
    private DadosEscolaresDTOResponse dadosEscolaresDTOResponse;
    private UpdateDadosEscolaresDTORequest updateDadosEscolaresDTORequest;

    private Aluno aluno = new Aluno();
    
    @BeforeEach
    void setUp() {
        dadosEscolares = new DadosEscolares("ESCOLA");

        dadosEscolaresDTORequest = new DadosEscolaresDTORequest("escola");

        dadosEscolaresDTOResponse = new DadosEscolaresDTOResponse("escola");

        updateDadosEscolaresDTORequest = new UpdateDadosEscolaresDTORequest("escola");
    }

    @Test
    void deveMapearAlunoParaDTOResponse() {

        dadosEscolaresDTOResponse = DadosEscolaresMapper
            .mapToDadosEscolaresDTOResponse(dadosEscolares);

        assertEquals(dadosEscolares.getEscola(), dadosEscolaresDTOResponse.escola());
    }

    @Test
    void deveMapearAlunoDTORequestDeAluno(){
        dadosEscolares = DadosEscolaresMapper.mapToDadosEscolares(dadosEscolaresDTORequest);

        assertEquals(dadosEscolaresDTORequest.escola(), dadosEscolares.getEscola());
    }

    @Test
    void deveMapearDadosEscolaresDeUpdateDadosEscolares(){
        dadosEscolares = DadosEscolaresMapper.updateDadosEscolares(updateDadosEscolaresDTORequest, aluno);
        assertEquals(updateDadosEscolaresDTORequest.escola(), dadosEscolares.getEscola());
    }
}
