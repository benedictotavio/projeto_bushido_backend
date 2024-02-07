package br.org.institutobushido.dtos.aluno;

import java.sql.Date;
import java.util.List;

import br.org.institutobushido.dtos.aluno.objects.dados_escolares.DadosEscolaresDTORequest;
import br.org.institutobushido.dtos.aluno.objects.dados_sociais.DadosSociaisDTORequest;
import br.org.institutobushido.dtos.aluno.objects.endereco.EnderecoDTORequest;
import br.org.institutobushido.dtos.aluno.objects.graduacao.GraduacaoDTORequest;
import br.org.institutobushido.dtos.aluno.objects.historico_de_saude.HistoricoSaudeDTORequest;
import br.org.institutobushido.dtos.aluno.objects.responsavel.ResponsavelDTORequest;
import br.org.institutobushido.enums.Genero;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record AlunoDTORequest(
        @NotNull(message = "Nome é obrigatório") String nome,

        @NotNull(message = "Data de nascimento é obrigatória") Date dataNascimento,

        @NotNull(message = "Genero é obrigatório") Genero genero,

        DadosSociaisDTORequest dadosSociais,

        DadosEscolaresDTORequest dadosEscolares,

        @NotNull(message = "Endereço é obrigatório!") EnderecoDTORequest endereco,

        @NotEmpty @Pattern(regexp = "^\\d{9}$", message = "Formato de rg inválido!") String rg,

        @NotEmpty @NotNull(message = "Insira pelo menos um Responsavel!") List<ResponsavelDTORequest> responsaveis,

        @NotNull(message = "Historico de Saude é obrigatório!") HistoricoSaudeDTORequest historicoSaude,

        @NotNull(message = "Graduacao é obrigatório!") GraduacaoDTORequest graduacao) {
}