package br.org.institutobushido.mappers.aluno;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import br.org.institutobushido.controllers.dtos.aluno.AlunoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.AlunoDTOResponse;
import br.org.institutobushido.models.aluno.Aluno;
import br.org.institutobushido.models.aluno.graduacao.Graduacao;
import org.springframework.web.multipart.MultipartFile;

public class AlunoMapper {
        private AlunoMapper() {
        }

        public static Aluno mapToAluno(AlunoDTORequest alunoDTORequest, MultipartFile imagemAluno) throws IOException {

                if (alunoDTORequest == null) {
                        return null;
                }

                Aluno aluno = new Aluno(
                                alunoDTORequest.cpf(),
                                alunoDTORequest.nome(),
                                new Date(alunoDTORequest.dataNascimento()),
                                alunoDTORequest.genero(),
                                alunoDTORequest.turma());

                aluno.adicionarGraduacao(
                                new Graduacao(alunoDTORequest.graduacao().kyu(), alunoDTORequest.graduacao().dan()));
                aluno.adicionarResponsavel(ResponsavelMapper.mapToResponsavel(alunoDTORequest.responsaveis()));
                aluno.setEndereco(EnderecoMapper.mapToEndereco(alunoDTORequest.endereco()));
                aluno.setImagemAluno(ImagemAlunoMapper.mapToImagemAluno(imagemAluno));
                aluno.setDadosSociais(DadosSociaisMapper.mapToDadosSociais(alunoDTORequest.dadosSociais()));
                aluno.setDadosEscolares(DadosEscolaresMapper.mapToDadosEscolares(alunoDTORequest.dadosEscolares()));
                aluno.setHistoricoSaude(HistoricoSaudeMapper.mapToHistoricoSaude(alunoDTORequest.historicoSaude()));

                return aluno;

        }

        public static Aluno mapToAluno(AlunoDTORequest alunoDTORequest){

                if (alunoDTORequest == null) {
                        return null;
                }

                Aluno aluno = new Aluno(
                        alunoDTORequest.cpf(),
                        alunoDTORequest.nome(),
                        new Date(alunoDTORequest.dataNascimento()),
                        alunoDTORequest.genero(),
                        alunoDTORequest.turma());

                aluno.adicionarGraduacao(
                        new Graduacao(alunoDTORequest.graduacao().kyu(), alunoDTORequest.graduacao().dan()));
                aluno.adicionarResponsavel(ResponsavelMapper.mapToResponsavel(alunoDTORequest.responsaveis()));
                aluno.setEndereco(EnderecoMapper.mapToEndereco(alunoDTORequest.endereco()));
                aluno.setDadosSociais(DadosSociaisMapper.mapToDadosSociais(alunoDTORequest.dadosSociais()));
                aluno.setDadosEscolares(DadosEscolaresMapper.mapToDadosEscolares(alunoDTORequest.dadosEscolares()));
                aluno.setHistoricoSaude(HistoricoSaudeMapper.mapToHistoricoSaude(alunoDTORequest.historicoSaude()));

                return aluno;

        }

        public static Aluno mapToAluno(AlunoDTOResponse alunoDTOResponse) {

                if (alunoDTOResponse == null) {
                        return null;
                }

                Aluno aluno = new Aluno(
                                alunoDTOResponse.cpf(),
                                alunoDTOResponse.nome(),
                                alunoDTOResponse.dataNascimento(),
                                alunoDTOResponse.genero(),
                                alunoDTOResponse.turma());

                aluno.setDadosSociais(
                                DadosSociaisMapper.mapToDadosSociais(alunoDTOResponse.dadosSociais()));
                aluno.setDadosEscolares(
                                DadosEscolaresMapper.mapToDadosEscolares(alunoDTOResponse.dadosEscolares()));
                aluno.setResponsaveis(
                                ResponsavelMapper.mapToListResponsaveis(alunoDTOResponse.responsaveis()));
                aluno.setEndereco(
                                EnderecoMapper.mapToEndereco(alunoDTOResponse.endereco()));
                aluno.setHistoricoSaude(
                                HistoricoSaudeMapper.mapToHistoricoSaude(alunoDTOResponse.historicoSaude()));
                aluno.setGraduacao(
                                GraduacaoMapper.mapToListGraduacao(alunoDTOResponse.graduacao()));
                aluno.setImagemAluno(
                                ImagemAlunoMapper.mapToImagemAluno(alunoDTOResponse.imagemAluno()));

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
                                .withCpf(aluno.getCpf())
                                .withTurma(aluno.getTurma())
                                .withResponsaveis(
                                                ResponsavelMapper.mapToResponsaveisDTOResponse(aluno.getResponsaveis()))
                                .withEndereco(EnderecoMapper.mapToEnderecoDTOResponse(aluno.getEndereco()))
                                .withDadosSociais(DadosSociaisMapper
                                                .mapToDadosSociaisDTOResponse(aluno.getDadosSociais()))
                                .withDadosEscolares(
                                                DadosEscolaresMapper.mapToDadosEscolaresDTOResponse(
                                                                aluno.getDadosEscolares()))
                                .withGraduacao(GraduacaoMapper.mapToListGraduacaoDTOResponse(aluno.getGraduacao()))
                                .withHistoricoSaude(
                                                HistoricoSaudeMapper.mapToHistoricoSaudeDTOResponse(
                                                                aluno.getHistoricoSaude()))
                                .withImagemAluno(
                                                ImagemAlunoMapper.mapToImagemAlunoDTOResponse(
                                                                aluno.getImagemAluno()))
                                .build();
        }

        public static List<AlunoDTOResponse> mapToListAlunoDTOResponse(List<Aluno> alunos) {
                return alunos.stream().map(AlunoMapper::mapToAlunoDTOResponse).collect(Collectors.toList());
        }
}
