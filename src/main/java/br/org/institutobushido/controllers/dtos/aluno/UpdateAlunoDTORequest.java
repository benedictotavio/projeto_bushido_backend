package br.org.institutobushido.controllers.dtos.aluno;

import br.org.institutobushido.controllers.dtos.aluno.dados_escolares.UpdateDadosEscolaresDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.dados_sociais.UpdateDadosSociaisDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.endereco.UpdateEnderecoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.UpdateHistoricoSaudeDTORequest;

public record UpdateAlunoDTORequest(
        UpdateDadosSociaisDTORequest dadosSociais,
        UpdateDadosEscolaresDTORequest dadosEscolares,
        UpdateEnderecoDTORequest endereco,
        UpdateHistoricoSaudeDTORequest historicoDeSaude
        ) {
}
