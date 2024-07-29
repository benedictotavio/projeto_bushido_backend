package br.org.institutobushido.providers.mappers.aluno;

import java.io.IOException;
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

                Aluno aluno = new Aluno();

                aluno.setCpf(alunoDTORequest.cpf());
                aluno.setNome(alunoDTORequest.nome());
                aluno.setDataNascimento(alunoDTORequest.dataNascimento());
                aluno.setGenero(alunoDTORequest.genero());
                aluno.setTurma(alunoDTORequest.turma());
                aluno.setCartaoSus(alunoDTORequest.cartaoSus());
                aluno.setEmail(alunoDTORequest.email());
                aluno.setTelefone(alunoDTORequest.telefone());
                aluno.setCorDePele(alunoDTORequest.corDePele());

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

        public static Aluno mapToAluno(AlunoDTORequest alunoDTORequest) {

                if (alunoDTORequest == null) {
                        return null;
                }

                Aluno aluno = new Aluno();

                aluno.setCpf(alunoDTORequest.cpf());
                aluno.setNome(alunoDTORequest.nome());
                aluno.setDataNascimento(alunoDTORequest.dataNascimento());
                aluno.setGenero(alunoDTORequest.genero());
                aluno.setTurma(alunoDTORequest.turma());
                aluno.setCartaoSus(alunoDTORequest.cartaoSus());
                aluno.setEmail(alunoDTORequest.email());
                aluno.setTelefone(alunoDTORequest.telefone());
                aluno.setCorDePele(alunoDTORequest.corDePele());

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

                Aluno aluno = new Aluno();

                aluno.setCpf(alunoDTOResponse.cpf());
                aluno.setNome(alunoDTOResponse.nome());
                aluno.setDataNascimento(alunoDTOResponse.dataNascimento().getTime());
                aluno.setGenero(alunoDTOResponse.genero());
                aluno.setTurma(alunoDTOResponse.turma());
                aluno.setCartaoSus(alunoDTOResponse.cartaoSus());
                aluno.setEmail(alunoDTOResponse.email());
                aluno.setTelefone(alunoDTOResponse.telefone());
                aluno.setCorDePele(alunoDTOResponse.corDePele());

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
                                .withCartaoSus(aluno.getCartaoSus())
                                .withEmail(aluno.getEmail())
                                .withCorDePele(aluno.getCorDePele())
                                .withTelefone(aluno.getTelefone())
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
