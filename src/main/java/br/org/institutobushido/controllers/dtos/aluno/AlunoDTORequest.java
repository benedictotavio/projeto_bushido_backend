package br.org.institutobushido.controllers.dtos.aluno;

import java.util.Date;
import java.util.List;

import br.org.institutobushido.controllers.dtos.aluno.endereco.EnderecoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.GraduacaoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.dados_escolares.DadosEscolaresDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.dados_sociais.DadosSociaisDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.HistoricoSaudeDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.responsavel.ResponsavelDTORequest;
import br.org.institutobushido.enums.aluno.Genero;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record AlunoDTORequest(
                @NotNull(message = "Nome é obrigatório") String nome,

                @NotNull(message = "Data de nascimento é obrigatória") @Past(message = "Data de nascimento deve ser no passado!") Date dataNascimento,

                @NotNull(message = "Genero é obrigatório") Genero genero,

                @NotNull(message = "Dados sociais é obrigatório") DadosSociaisDTORequest dadosSociais,

                @NotNull(message = "Dados escolares é obrigatório") DadosEscolaresDTORequest dadosEscolares,

                @NotNull(message = "Endereço é obrigatório!") EnderecoDTORequest endereco,

                @NotNull(message = "Rg é obrigatório!") @Pattern(regexp = "^\\d{9}$", message = "Formato de rg inválido!") String rg,

                @NotEmpty(message = "Insira pelo menos um Responsavel!") @Size(min = 1, message = "Insira pelo menos um Responsavel!") List<ResponsavelDTORequest> responsaveis,

                @NotNull(message = "Historico de Saude é obrigatório!") HistoricoSaudeDTORequest historicoSaude,

                GraduacaoDTORequest graduacao) {
}