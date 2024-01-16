package br.org.institutobushido.dtos.aluno;

import java.util.Date;
import java.util.List;

import br.org.institutobushido.dtos.aluno.objects.dados_escolares.DadosEscolaresDTOResponse;
import br.org.institutobushido.dtos.aluno.objects.dados_sociais.DadosSociaisDTOResponse;
import br.org.institutobushido.dtos.aluno.objects.endereco.EnderecoDTOResponse;
import br.org.institutobushido.dtos.aluno.objects.graduacao.GraduacaoDTOResponse;
import br.org.institutobushido.dtos.aluno.objects.responsavel.ResponsavelDTOResponse;
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
        GraduacaoDTOResponse graduacao) {
}
