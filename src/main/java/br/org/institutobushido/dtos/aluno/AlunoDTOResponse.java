package br.org.institutobushido.dtos.aluno;

import java.util.Date;
import java.util.List;

import br.org.institutobushido.dtos.aluno.dados_escolares.DadosEscolaresDTOResponse;
import br.org.institutobushido.dtos.aluno.dados_sociais.DadosSociaisDTOResponse;
import br.org.institutobushido.dtos.aluno.endereco.EnderecoDTOResponse;
import br.org.institutobushido.dtos.aluno.graduacao.GraduacaoDTOResponse;
import br.org.institutobushido.dtos.aluno.historico_de_saude.HistoricoSaudeDTOResponse;
import br.org.institutobushido.dtos.aluno.responsavel.ResponsavelDTOResponse;
import br.org.institutobushido.enums.Genero;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record AlunoDTOResponse(
                String nome,
                Date dataNascimento,
                Genero genero,
                DadosSociaisDTOResponse dadosSociais,
                DadosEscolaresDTOResponse dadosEscolares,
                Date dataPreenchimento,
                EnderecoDTOResponse endereco,
                String rg,
                List<ResponsavelDTOResponse> responsaveis,
                GraduacaoDTOResponse graduacao,
                HistoricoSaudeDTOResponse historicoSaude) {
}
