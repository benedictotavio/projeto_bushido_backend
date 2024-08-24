package br.org.institutobushido.providers.mappers.aluno;

import br.org.institutobushido.controllers.dtos.aluno.dados_escolares.DadosEscolaresDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.dados_escolares.DadosEscolaresDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.dados_escolares.UpdateDadosEscolaresDTORequest;
import br.org.institutobushido.models.aluno.Aluno;
import br.org.institutobushido.models.aluno.dados_escolares.DadosEscolares;

public class DadosEscolaresMapper {
    private DadosEscolaresMapper() {
    }

    public static DadosEscolares updateDadosEscolares(UpdateDadosEscolaresDTORequest dadosEscolaresDTORequest,
            Aluno aluno) {

        if (dadosEscolaresDTORequest == null) {
            return null;
        }
        aluno.getDadosEscolares().setEscola(dadosEscolaresDTORequest.escola());
        return aluno.getDadosEscolares();
    }

    public static DadosEscolares mapToDadosEscolares(DadosEscolaresDTORequest dadosEscolaresDTORequest) {

        if (dadosEscolaresDTORequest == null) {
            return null;
        }

        DadosEscolares dadosEscolares = new DadosEscolares();
        dadosEscolares.setEscola(dadosEscolaresDTORequest.escola());

        return dadosEscolares;
    }

    public static DadosEscolares mapToDadosEscolares(DadosEscolaresDTOResponse dadosEscolaresDTOResponse) {

        if (dadosEscolaresDTOResponse == null) {
            return null;
        }

        DadosEscolares dadosEscolares = new DadosEscolares();
        dadosEscolares.setEscola(dadosEscolaresDTOResponse.escola());

        return dadosEscolares;
    }

    public static DadosEscolaresDTOResponse mapToDadosEscolaresDTOResponse(DadosEscolares dadosEscolares) {
        if (dadosEscolares == null) {
            return null;
        }

        return DadosEscolaresDTOResponse.builder()
                .withEscola(dadosEscolares.getEscola())
                .build();
    }
}
