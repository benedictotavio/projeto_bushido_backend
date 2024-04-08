package br.org.institutobushido.mappers.aluno;

import java.util.List;

import br.org.institutobushido.controllers.dtos.aluno.AlunoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.AlunoDTOResponse;
import br.org.institutobushido.models.aluno.Aluno;
import br.org.institutobushido.models.aluno.graduacao.Graduacao;

public class AlunoMapper {
    private AlunoMapper() {
    }

    public static Aluno mapToAluno(AlunoDTORequest alunoDTORequest) {
        if (alunoDTORequest == null) {
            return null;
        }

        Aluno aluno = new Aluno(alunoDTORequest.rg());
        aluno.setGenero(alunoDTORequest.genero());
        aluno.setDataNascimento(alunoDTORequest.dataNascimento());
        aluno.setNome(alunoDTORequest.nome());
        aluno.adicionarGraduacao(new Graduacao(alunoDTORequest.graduacao().kyu()));
        aluno.setResponsaveis(ResponsavelMapper.mapToResponsaveis(alunoDTORequest.responsaveis()));
        aluno.setEndereco(EnderecoMapper.mapToEndereco(alunoDTORequest.endereco()));
        aluno.setDadosSociais(DadosSociaisMapper.mapToDadosSociais(alunoDTORequest.dadosSociais()));
        aluno.setDadosEscolares(DadosEscolaresMapper.mapToDadosEscolares(alunoDTORequest.dadosEscolares()));
        aluno.setHistoricoSaude(HistoricoSaudeMapper.mapToHistoricoSaude(alunoDTORequest.historicoSaude()));

        return aluno;

    }

    public static AlunoDTOResponse mapToAlunoDTOResponse(Aluno aluno) {
        if (aluno == null) {
            return null;
        }

        return AlunoDTOResponse.builder()
                .withNome(aluno.getNome())
                .withGenero(aluno.getGenero())
                .withDataNascimento(aluno.getDataNascimento())
                .withDataPreenchimento(aluno.getDataPreenchimento())
                .withRg(aluno.getRg())
                .withResponsaveis(ResponsavelMapper.mapToResponsaveisDTOResponse(aluno.getResponsaveis()))
                .withEndereco(EnderecoMapper.mapToEnderecoDTOResponse(aluno.getEndereco()))
                .withDadosSociais(DadosSociaisMapper.mapToDadosSociaisDTOResponse(aluno.getDadosSociais()))
                .withDadosEscolares(
                        DadosEscolaresMapper.mapToDadosEscolaresDTOResponse(aluno.getDadosEscolares()))
                .withGraduacao(GraduacaoMapper.mapToListGraduacaoDTOResponse(aluno.getGraduacao()))
                .withHistoricoSaude(
                        HistoricoSaudeMapper.mapToHistoricoSaudeDTOResponse(aluno.getHistoricoSaude()))
                .build();
    }

    public static List<AlunoDTOResponse> mapToListAlunoDTOResponse(List<Aluno> alunos) {
        return alunos.stream().map(AlunoMapper::mapToAlunoDTOResponse).toList();
    }
}
