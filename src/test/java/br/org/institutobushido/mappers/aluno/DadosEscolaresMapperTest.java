package br.org.institutobushido.mappers.aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.institutobushido.controllers.dtos.aluno.dados_escolares.DadosEscolaresDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.dados_escolares.DadosEscolaresDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.dados_escolares.UpdateDadosEscolaresDTORequest;
import br.org.institutobushido.enums.aluno.Genero;
import br.org.institutobushido.enums.aluno.Turno;
import br.org.institutobushido.models.aluno.Aluno;
import br.org.institutobushido.models.aluno.dados_escolares.DadosEscolares;

@SpringBootTest
class DadosEscolaresMapperTest {

    private DadosEscolares dadosEscolares;
    private DadosEscolaresDTORequest dadosEscolaresDTORequest;
    private DadosEscolaresDTOResponse dadosEscolaresDTOResponse;
    private UpdateDadosEscolaresDTORequest updateDadosEscolaresDTORequest;

    private Aluno aluno = new Aluno("123456789", "nome", new Date(), Genero.M);
    
    @BeforeEach
    void setUp() {
        dadosEscolares = new DadosEscolares(
            Turno.MANHA,
            "escola",
            "serie"
        );

        dadosEscolaresDTORequest = new DadosEscolaresDTORequest(
            Turno.MANHA,
            "escola",
            "serie"
        );

        dadosEscolaresDTOResponse = new DadosEscolaresDTOResponse(
            Turno.MANHA,
            "escola",
            "serie"
        );

        updateDadosEscolaresDTORequest = new UpdateDadosEscolaresDTORequest(
            Turno.MANHA,
            "escola",
            "serie"
        );
    }

    @Test
    void deveMapearAlunoParaDTOResponse() {

        dadosEscolaresDTOResponse = DadosEscolaresMapper
            .mapToDadosEscolaresDTOResponse(dadosEscolares);

        assertEquals(dadosEscolares.getTurno(), dadosEscolaresDTOResponse.turno());
        assertEquals(dadosEscolares.getEscola(), dadosEscolaresDTOResponse.escola());
        assertEquals(dadosEscolares.getSerie(), dadosEscolaresDTOResponse.serie());
    }

    @Test
    void deveMapearAlunoDTORequestDeAluno(){
        dadosEscolares = DadosEscolaresMapper.mapToDadosEscolares(dadosEscolaresDTORequest);

        assertEquals(dadosEscolaresDTORequest.turno(), dadosEscolares.getTurno());
        assertEquals(dadosEscolaresDTORequest.escola(), dadosEscolares.getEscola());
        assertEquals(dadosEscolaresDTORequest.serie(), dadosEscolares.getSerie());
    }

    @Test
    void deveMapearDadosEscolaresDeUpdateDadosEscolares(){
        dadosEscolares = DadosEscolaresMapper.updateDadosEscolares(updateDadosEscolaresDTORequest, aluno);

        assertEquals(updateDadosEscolaresDTORequest.turno(), dadosEscolares.getTurno());
        assertEquals(updateDadosEscolaresDTORequest.escola(), dadosEscolares.getEscola());
        assertEquals(updateDadosEscolaresDTORequest.serie(), dadosEscolares.getSerie());
    }
}
