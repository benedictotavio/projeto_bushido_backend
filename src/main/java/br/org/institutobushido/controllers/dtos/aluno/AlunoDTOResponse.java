package br.org.institutobushido.controllers.dtos.aluno;

import java.util.Date;
import java.util.List;

import br.org.institutobushido.controllers.dtos.aluno.endereco.EnderecoDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.GraduacaoDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.dados_escolares.DadosEscolaresDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.dados_sociais.DadosSociaisDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.HistoricoSaudeDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.responsavel.ResponsavelDTOResponse;
import br.org.institutobushido.enums.aluno.Genero;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record AlunoDTOResponse(
                String nome,
                Date dataNascimento,
                Genero genero,
                String turma,
                DadosSociaisDTOResponse dadosSociais,
                DadosEscolaresDTOResponse dadosEscolares,
                Date dataPreenchimento,
                EnderecoDTOResponse endereco,
                String rg,
                List<ResponsavelDTOResponse> responsaveis,
                List<GraduacaoDTOResponse> graduacao,
                HistoricoSaudeDTOResponse historicoSaude) {
}
