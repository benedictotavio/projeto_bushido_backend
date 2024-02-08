package br.org.institutobushido.mappers;

import br.org.institutobushido.dtos.aluno.dados_escolares.DadosEscolaresDTORequest;
import br.org.institutobushido.dtos.aluno.dados_escolares.DadosEscolaresDTOResponse;
import br.org.institutobushido.model.aluno.dados_escolares.DadosEscolares;

public class DadosEscolaresMapper {
    private DadosEscolaresMapper() {
    }

    public static DadosEscolares mapToDadosEscolares(DadosEscolaresDTORequest dadosEscolaresDTORequest) {

        if (dadosEscolaresDTORequest == null) {
            return null;
        }

       DadosEscolares dadosEscolares = new DadosEscolares();
       dadosEscolares.setEscola(dadosEscolaresDTORequest.escola());
       dadosEscolares.setSerie(dadosEscolaresDTORequest.serie());
       dadosEscolares.setTurno(dadosEscolaresDTORequest.turno());

       return dadosEscolares;
    }

    public static DadosEscolaresDTOResponse mapToDadosEscolaresDTOResponse(DadosEscolares dadosEscolares) {
        if (dadosEscolares == null) {
            return null;
        }

        return DadosEscolaresDTOResponse.builder().withEscola(dadosEscolares.getEscola()).withSerie(dadosEscolares.getSerie()).withTurno(dadosEscolares.getTurno()).build();
    }
}
