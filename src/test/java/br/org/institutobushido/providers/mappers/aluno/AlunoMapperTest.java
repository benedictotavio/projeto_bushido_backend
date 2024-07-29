package br.org.institutobushido.providers.mappers.aluno;

import br.org.institutobushido.controllers.dtos.aluno.AlunoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.AlunoDTOResponse;
import br.org.institutobushido.models.aluno.Aluno;
import br.org.institutobushido.models.aluno.dados_escolares.DadosEscolares;
import br.org.institutobushido.models.aluno.dados_sociais.DadosSociais;
import br.org.institutobushido.models.aluno.endereco.Endereco;
import br.org.institutobushido.models.aluno.graduacao.Graduacao;
import br.org.institutobushido.models.aluno.historico_de_saude.HistoricoSaude;
import br.org.institutobushido.models.aluno.historico_de_saude.informacoes_saude.Alergia;
import br.org.institutobushido.models.aluno.historico_de_saude.informacoes_saude.Cirurgia;
import br.org.institutobushido.models.aluno.historico_de_saude.informacoes_saude.DoencaCronica;
import br.org.institutobushido.models.aluno.historico_de_saude.informacoes_saude.UsoMedicamentoContinuo;
import br.org.institutobushido.models.aluno.responsaveis.Responsavel;
import br.org.institutobushido.providers.enums.aluno.FiliacaoResposavel;
import br.org.institutobushido.providers.enums.aluno.Genero;
import br.org.institutobushido.providers.enums.aluno.Imovel;
import br.org.institutobushido.providers.enums.aluno.TipoSanguineo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AlunoMapperTest {
    private AlunoDTORequest alunoDTORequest;
    private Aluno aluno;
    private AlunoDTOResponse alunoDTOResponse;

    @BeforeEach
    void setUp() {
        alunoDTORequest = AlunoDTORequest.builder().build();

        aluno = new Aluno();

        aluno.setNome("NOME");
        aluno.setCpf("11111111111");
        aluno.setDataNascimento(new Date().getTime());
        aluno.setGenero(Genero.M);


        aluno.setDadosEscolares(
                new DadosEscolares("ESCOLA"));

        aluno.setEndereco(
                new Endereco(
                        "CIDADE",
                        "ESTADO",
                        "CEP",
                        "100",
                        "LOGRADOURO"));

        aluno.setDadosSociais(
                new DadosSociais(
                        false,
                        false,
                        Imovel.PROPRIO,
                        5,
                        2,
                        false,
                        0));

        aluno.setHistoricoSaude(
                new HistoricoSaude(
                        TipoSanguineo.O_POSITIVO,
                        new UsoMedicamentoContinuo("Tipo"),
                        new DoencaCronica("Doenca"),
                        new Alergia("Alergia"),
                        new Cirurgia("Cirurgia"),
                        List.of("Deficiencia"),
                        List.of("Acompanhamento")));

        aluno.adicionarGraduacao(
                new Graduacao(7, new ArrayList<>(), true, 100, LocalDate.now().minusMonths(3),
                        LocalDate.now().plusMonths(3), false, 80, 1, 10));

        aluno.adicionarResponsavel(
                new Responsavel("Nome", "12345678901", "Email", "Telefone", FiliacaoResposavel.OUTRO));

        alunoDTOResponse = AlunoDTOResponse.builder().build();
    }

    @Test
    void deveMapearAlunoDTORequestToAluno() {
        aluno = AlunoMapper.mapToAluno(alunoDTORequest);
        assertEquals(alunoDTORequest.nome(), aluno.getNome());
        assertEquals(alunoDTORequest.cpf(), aluno.getCpf());
        assertEquals(alunoDTORequest.dataNascimento(), aluno.getDataNascimento().getTime());
        assertEquals(alunoDTORequest.genero(), aluno.getGenero());
        assertEquals(alunoDTORequest.dadosSociais().auxilioBrasil(), aluno.getDadosSociais().isAuxilioBrasil());
        assertEquals(alunoDTORequest.dadosEscolares().escola(), aluno.getDadosEscolares().getEscola());
        assertEquals(alunoDTORequest.endereco().estado(), aluno.getEndereco().getEstado());
        assertEquals(alunoDTORequest.responsaveis().email(), aluno.getResponsaveis().get(0).getEmail());
        assertEquals(alunoDTORequest.historicoSaude().alergia().tipo(),
                aluno.getHistoricoSaude().getAlergia().getTipo());
    }

    @Test
    void deveMapearAlunoDTOResponseDeAluno() {
        alunoDTOResponse = AlunoMapper.mapToAlunoDTOResponse(aluno);
        assertEquals(aluno.getNome(), alunoDTOResponse.nome());
        assertEquals(aluno.getCpf(), alunoDTOResponse.cpf());
        assertEquals(aluno.getDataNascimento(), alunoDTOResponse.dataNascimento());
        assertEquals(aluno.getGenero(), alunoDTOResponse.genero());
        assertEquals(aluno.getDadosSociais().isAuxilioBrasil(),
                alunoDTOResponse.dadosSociais().auxilioBrasil());
        assertEquals(aluno.getDadosEscolares().getEscola(), alunoDTOResponse.dadosEscolares().escola());
        assertEquals(aluno.getEndereco().getEstado(), alunoDTOResponse.endereco().estado());
        assertEquals(aluno.getHistoricoSaude().getAlergia().getTipo(),
                alunoDTOResponse.historicoSaude().alergia().tipo());
    }

    @Test
    void deveMapearUmaListaAlunoParaUmaListaAlunoDTOResponse() {
        List<Aluno> alunos = List.of(aluno);
        List<AlunoDTOResponse> alunosDTOResponses = AlunoMapper.mapToListAlunoDTOResponse(alunos);
        assertEquals(alunos.get(0).getNome(), alunosDTOResponses.get(0).nome());
        assertEquals(alunos.get(0).getCpf(), alunosDTOResponses.get(0).cpf());
        assertEquals(alunos.get(0).getDataNascimento(), alunosDTOResponses.get(0).dataNascimento());
        assertEquals(alunos.get(0).getGenero(), alunosDTOResponses.get(0).genero());
    }

}