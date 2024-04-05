package br.org.institutobushido.controllers.dtos.aluno;

import java.util.Date;

import br.org.institutobushido.controllers.dtos.aluno.dados_escolares.UpdateDadosEscolaresDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.dados_sociais.UpdateDadosSociaisDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.endereco.UpdateEnderecoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.UpdateGraduacaoDTORequest;
import br.org.institutobushido.enums.aluno.Genero;

public record UpdateAlunoDTORequest(
        String nome,
        Date dataNascimento,
        Genero genero,
        UpdateDadosSociaisDTORequest dadosSociais,
        UpdateDadosEscolaresDTORequest dadosEscolares,
        UpdateEnderecoDTORequest endereco,
        UpdateGraduacaoDTORequest graduacao) {
}
