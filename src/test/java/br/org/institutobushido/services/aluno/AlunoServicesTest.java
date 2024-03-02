package br.org.institutobushido.services.aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoException;

import br.org.institutobushido.dtos.aluno.AlunoDTORequest;
import br.org.institutobushido.dtos.aluno.AlunoDTOResponse;
import br.org.institutobushido.dtos.aluno.dados_escolares.DadosEscolaresDTORequest;
import br.org.institutobushido.dtos.aluno.dados_escolares.DadosEscolaresDTOResponse;
import br.org.institutobushido.dtos.aluno.dados_sociais.DadosSociaisDTORequest;
import br.org.institutobushido.dtos.aluno.dados_sociais.DadosSociaisDTOResponse;
import br.org.institutobushido.dtos.aluno.endereco.EnderecoDTORequest;
import br.org.institutobushido.dtos.aluno.endereco.EnderecoDTOResponse;
import br.org.institutobushido.dtos.aluno.graduacao.GraduacaoDTORequest;
import br.org.institutobushido.dtos.aluno.graduacao.GraduacaoDTOResponse;
import br.org.institutobushido.dtos.aluno.graduacao.faltas.FaltaDTORequest;
import br.org.institutobushido.dtos.aluno.historico_de_saude.HistoricoSaudeDTORequest;
import br.org.institutobushido.dtos.aluno.historico_de_saude.HistoricoSaudeDTOResponse;
import br.org.institutobushido.dtos.aluno.historico_de_saude.informacoes_de_saude.alergia.AlergiaDTORequest;
import br.org.institutobushido.dtos.aluno.historico_de_saude.informacoes_de_saude.cirurgia.CirurgiaDTORequest;
import br.org.institutobushido.dtos.aluno.historico_de_saude.informacoes_de_saude.doenca_cronica.DoencaCronicaDTORequest;
import br.org.institutobushido.dtos.aluno.historico_de_saude.informacoes_de_saude.uso_medicamento_continuo.UsoMedicamentoContinuoDTORequest;
import br.org.institutobushido.dtos.aluno.responsavel.ResponsavelDTORequest;
import br.org.institutobushido.dtos.aluno.responsavel.ResponsavelDTOResponse;
import br.org.institutobushido.enums.aluno.FatorRH;
import br.org.institutobushido.enums.aluno.FiliacaoResposavel;
import br.org.institutobushido.enums.aluno.TipoSanguineo;
import br.org.institutobushido.enums.aluno.Turno;
import br.org.institutobushido.model.aluno.Aluno;
import br.org.institutobushido.model.aluno.dados_escolares.DadosEscolares;
import br.org.institutobushido.model.aluno.dados_sociais.DadosSociais;
import br.org.institutobushido.model.aluno.endereco.Endereco;
import br.org.institutobushido.model.aluno.graduacao.Graduacao;
import br.org.institutobushido.model.aluno.graduacao.falta.Falta;
import br.org.institutobushido.model.aluno.historico_de_saude.HistoricoSaude;
import br.org.institutobushido.model.aluno.historico_de_saude.informacoes_saude.Alergias;
import br.org.institutobushido.model.aluno.historico_de_saude.informacoes_saude.Cirurgias;
import br.org.institutobushido.model.aluno.historico_de_saude.informacoes_saude.DoencaCronica;
import br.org.institutobushido.model.aluno.historico_de_saude.informacoes_saude.UsoMedicamentoContinuo;
import br.org.institutobushido.model.aluno.responsaveis.Responsavel;
import br.org.institutobushido.repositories.AlunoRepositorio;
import br.org.institutobushido.resources.exceptions.AlreadyRegisteredException;
import br.org.institutobushido.resources.exceptions.EntityNotFoundException;

@SpringBootTest
class AlunoServicesTest {

    private Aluno aluno;
    private List<Responsavel> responsaveis = new ArrayList<>();
    private List<ResponsavelDTORequest> responsaveisDTORequest = new ArrayList<>();
    private EnderecoDTORequest enderecoDTORequest;
    private AlunoDTORequest alunoDtoRequest;
    private AlunoDTOResponse alunoDtoResponse;

    @BeforeEach
    void setUp() {
        aluno = new Aluno();

        alunoDtoRequest = AlunoDTORequest.builder()
                .withNome("João Algo")
                .withDadosSociais(new DadosSociaisDTORequest(false, false, null, 0, 0, false, 0))
                .withDadosEscolares(new DadosEscolaresDTORequest(Turno.NOITE, "Test", "Test"))
                .withEndereco(enderecoDTORequest)
                .withRg("123456789")
                .withResponsaveis(responsaveisDTORequest)
                .withGraduacao(new GraduacaoDTORequest(0, 0))
                .withDataNascimento(new Date(192912881000L))
                .withHistoricoSaude(
                        new HistoricoSaudeDTORequest(new UsoMedicamentoContinuoDTORequest(false, "tipo", "medicamento"),
                                new AlergiaDTORequest(false, "alergia"), new CirurgiaDTORequest(false, "cirurgia"),
                                new DoencaCronicaDTORequest(false, "doenca"), new ArrayList<String>(),
                                new ArrayList<String>()))
                .build();

        aluno.setNome(alunoDtoRequest.nome());
        aluno.setDadosSociais(new DadosSociais());
        aluno.setDadosEscolares(new DadosEscolares());
        aluno.setRg(alunoDtoRequest.rg());
        aluno.setGraduacao(new Graduacao(5, new ArrayList<Falta>(), false, 75));
        aluno.setResponsaveis(responsaveis);
        aluno.setEndereco(new Endereco());
        aluno.setDataNascimento(alunoDtoRequest.dataNascimento());
        aluno.setHistoricoSaude(new HistoricoSaude(TipoSanguineo.A_NEGATIVO, FatorRH.POSITIVO,
                new UsoMedicamentoContinuo(false, "medicamento", "medicamento"), new DoencaCronica(false, "doenca"),
                new Alergias(false, "alergia"), new Cirurgias(false, "cirurgia"), List.of("deficiencia"),
                List.of("acompanhamentoSaude")));

        reset(alunoRepositorio);
    }

    @Mock
    private AlunoRepositorio alunoRepositorio;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private AlunoServices alunoServices;

    @Test
    void deveRetornarTrueParaMetodoSaveForChamado() {
        // Arrange
        when(alunoRepositorio.save(any(Aluno.class))).thenReturn(aluno);

        // Act
        AlunoDTOResponse result = alunoServices.adicionarAluno(alunoDtoRequest);

        alunoDtoResponse = AlunoDTOResponse.builder()
                .withNome(aluno.getNome())
                .withDadosSociais(
                        new DadosSociaisDTOResponse(aluno.getDadosSociais().isBolsaFamilia(),
                                aluno.getDadosSociais().isAuxilioBrasil(), aluno.getDadosSociais().getImovel(),
                                aluno.getDadosSociais().getNumerosDePessoasNaCasa(),
                                aluno.getDadosSociais().getContribuintesDaRendaFamiliar(),
                                aluno.getDadosSociais().isAlunoContribuiParaRenda(),
                                aluno.getDadosSociais().getRendaFamiliarEmSalariosMinimos()))
                .withDadosEscolares(new DadosEscolaresDTOResponse(aluno.getDadosEscolares().getTurno(),
                        aluno.getDadosEscolares().getEscola(), aluno.getDadosEscolares().getSerie()))
                .withDataPreenchimento(aluno.getDataPreenchimento())
                .withEndereco(new EnderecoDTOResponse(aluno.getEndereco().getCidade(), aluno.getEndereco().getEstado(),
                        aluno.getEndereco().getCep(), aluno.getEndereco().getNumero()))
                .withRg(aluno.getRg())
                .withResponsaveis(new ArrayList<>())
                .withGraduacao(new GraduacaoDTOResponse(aluno.getGraduacao().getKyu(), aluno.getGraduacao().getFaltas(),
                        aluno.getGraduacao().isStatus(), aluno.getGraduacao().getFrequencia()))
                .withHistoricoSaude(
                        new HistoricoSaudeDTOResponse(null, null, null, null, List.of("deficiencia"),
                                List.of("acompanhamentoSaude")))
                .build();

        // Assert
        assertNotNull(result);
        assertEquals(alunoDtoResponse.dadosEscolares(), result.dadosEscolares());
        assertEquals(alunoDtoResponse.dadosSociais(), result.dadosSociais());
        assertEquals(alunoDtoResponse.dataPreenchimento(), result.dataPreenchimento());
    }

    @Test
    void deveConfirmarAsIntanciasDosValores() {
        when(alunoRepositorio.save(any(Aluno.class))).thenReturn(aluno);
        AlunoDTOResponse result = alunoServices.adicionarAluno(alunoDtoRequest);
        assertEquals(aluno.getNome(), result.nome());
        assertEquals(aluno.getDadosSociais().isBolsaFamilia(), result.dadosSociais().bolsaFamilia());
        assertEquals(aluno.getDadosSociais().getImovel(), result.dadosSociais().imovel());
        assertEquals(aluno.getDadosSociais().getRendaFamiliarEmSalariosMinimos(),
                result.dadosSociais().rendaFamiliarEmSalariosMinimos());
        assertEquals(aluno.getDataPreenchimento(), result.dataPreenchimento());
    }

    @Test
    void deveRetornarAlunoPorRgValido() {
        // Arrange
        Optional<Aluno> alunoTest = Optional.of(aluno);
        String validRg = "123456789";
        when(alunoRepositorio.findByRg(validRg)).thenReturn(alunoTest);

        // Act
        AlunoDTOResponse response = alunoServices.buscarAlunoPorRg(validRg);

        // Assert
        assertNotNull(response);
    }

    @Test
    void deveRetornarAlunoSeForEncontradoPorRg() {
        String rg = "43";
        Aluno aluno = new Aluno();
        aluno.setRg(rg);
        when(alunoRepositorio.findByRg(rg)).thenReturn(Optional.of(aluno));

        Aluno result = alunoRepositorio.findByRg(rg)
                .orElseThrow(() -> new MongoException("Email: " + rg + " não encontrado"));

        assertEquals(aluno, result);
    }

    @Test
    void deveRetornarExceptionSeAlunoNaoForEncontrado() {
        AlunoRepositorio alunoRepositorio = mock(AlunoRepositorio.class);
        when(alunoRepositorio.findByRg(Mockito.anyString())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            alunoServices.encontrarAlunoPorRg("nonexistent_rg");
        });
    }

    @Test
    void deveRetornarExceptionSeRgDoAlunoForPassadoComoNull() {
        assertThrows(EntityNotFoundException.class, () -> {
            alunoServices.encontrarAlunoPorRg(null);
        });
    }

    @Test
    void adicionarResponavelSeOAlunoPossuirMenosDe5Responsaveis() {
        // Arrange
        Optional<Aluno> alunoTest = Optional.of(aluno);
        String validRg = "123456789";
        when(alunoRepositorio.findByRg(validRg)).thenReturn(alunoTest);
        ResponsavelDTORequest responsavelDTORequest = ResponsavelDTORequest.builder().withCpf("123456789")
                .withEmail("test@example.com").withFiliacao(FiliacaoResposavel.OUTRO).withNome("John Doe")
                .withTelefone("30003000").build();
        // Act
        ResponsavelDTOResponse result = alunoServices.adicionarResponsavel(validRg, responsavelDTORequest);

        // Assert
        assertNotNull(result);
        assertEquals("123456789", result.cpf());
        assertEquals("test@example.com", result.email());
        assertEquals("OUTRO", result.filiacao());
        assertEquals("John Doe", result.nome());
        assertEquals("30003000", result.telefone());
    }

    @Test
    void deveRetornarOResponsavelSeExistir() {
        Responsavel responsavel1 = new Responsavel();
        responsavel1.setCpf("123456789");
        Responsavel responsavel2 = new Responsavel();
        responsavel2.setCpf("987654321");
        aluno.getResponsaveis().add(responsavel1);
        aluno.getResponsaveis().add(responsavel2);

        Optional<Responsavel> result = alunoServices.encontrarResponsavelPorCpf(aluno, "123456789");

        assertTrue(result.isPresent());
        assertEquals(responsavel1, result.get());
    }

    @Test
    void deveRetornarUmArrayVazio() {
        Optional<Responsavel> result = alunoServices.encontrarResponsavelPorCpf(aluno, "123456789");
        assertFalse(result.isPresent());
    }

    @Test
    void deveRemoverUmResponsavel() {
        // Arrange
        String rg = "123456";
        String cpf = "987654";
        Optional<Aluno> alunoTest = Optional.of(aluno);
        when(alunoRepositorio.findByRg(rg)).thenReturn(alunoTest);

        // Mocking the aluno object
        aluno.setRg(rg);
        Responsavel responsavel = new Responsavel();
        Responsavel responsavel2 = new Responsavel();
        responsavel.setCpf(cpf);
        aluno.getResponsaveis().add(responsavel);
        aluno.getResponsaveis().add(responsavel2);

        // Mocking the alunoRepositorio
        AlunoRepositorio alunoRepositorio = mock(AlunoRepositorio.class);
        when(alunoRepositorio.findByRg(rg)).thenReturn(Optional.of(aluno));

        // Act
        String result = alunoServices.removerResponsavel(rg, cpf);

        // Assert
        assertEquals("1", result);
    }

    @Test
    void deveAdicionarFaltasSeAlunoExistir() {
        // Arrange
        aluno.setGraduacao(new Graduacao(1, new ArrayList<Falta>(), true, 50));
        Optional<Aluno> alunoTest = Optional.of(aluno);
        String validRg = "123456789";
        when(alunoRepositorio.findByRg(validRg)).thenReturn(alunoTest);

        // Act
        FaltaDTORequest falta1 = new FaltaDTORequest("motivo", "observação");

        String result = alunoServices.adicionarFaltaDoAluno(validRg, falta1);

        // Arrange
        assertNotNull(result);
    }

    @Test
    void deveRemoverFaltasSeAlunoExistir() {
        // Arrange
        Falta faltaRemovida = new Falta("motivo", "observação");

        aluno.setGraduacao(new Graduacao(1, List.of(faltaRemovida), true, 50));
        Optional<Aluno> alunoTest = Optional.of(aluno);
        String validRg = "123456789";
        when(alunoRepositorio.findByRg(validRg)).thenReturn(alunoTest);

        // Act
        String result = alunoServices.retirarFaltaDoAluno(validRg, faltaRemovida.getData());

        // Arrange
        assertNotNull(result);
        assertEquals(0, Integer.parseInt(result));
    }

    @Test
    void deveRetornarUmaExceçãoSeRgForInvalido() {
        aluno.getGraduacao().setFaltas(List.of(new Falta("motivo", "observação")));
        String invalidRg = "00000000";
        String faltasId = aluno.getGraduacao().getFaltas().get(0).getData();
        AlunoRepositorio alunoRepositorio = mock(AlunoRepositorio.class);
        when(alunoRepositorio.findByRg(Mockito.anyString())).thenReturn(Optional.of(aluno));

        // Assert
        assertThrows(EntityNotFoundException.class, () -> {
            alunoServices.retirarFaltaDoAluno(invalidRg, faltasId);
        });
    }

    @Test
    void deveRetornarUmaFaltaSeExistir() {
        Falta falta1 = new Falta("motivo", "observacao");
        aluno.setGraduacao(new Graduacao(1, List.of(falta1), true, 50));
        Falta result = alunoServices.encontrarFaltasDoAluno(aluno, falta1.getData());
        assertNotNull(result);
        assertEquals(result, falta1);
    }

    @Test
    void deveAdicionarUmaDeficienciaNoHistoricoDeSaude() {
        HistoricoSaude hs = new HistoricoSaude(TipoSanguineo.AB_POSITIVO, FatorRH.POSITIVO,
                new UsoMedicamentoContinuo(false, "tipo", "atendimento"), null, null, null, null, null);

        hs.setDeficiencias(List.of("mancamento"));
        hs.setAcompanhamentoSaude(List.of("atendimento"));
        aluno.setHistoricoSaude(hs);

        when(alunoRepositorio.findByRg(Mockito.anyString())).thenReturn(Optional.of(aluno));

        String deficiencia = "Visual impairment";

        String result = alunoServices.adicionarDeficiencia("1234561111", deficiencia);

        assertEquals(deficiencia, result);
    }

    @Test
    void deveRetornarUmaExcessaoSeDeficienciaJaExistir() {

        HistoricoSaude hs = new HistoricoSaude(TipoSanguineo.AB_POSITIVO, FatorRH.POSITIVO,
                new UsoMedicamentoContinuo(false, "tipo", "atendimento"), null, null, null, null, null);

        hs.setDeficiencias(List.of("Physical disability"));
        hs.setAcompanhamentoSaude(List.of("atendimento"));
        aluno.setHistoricoSaude(hs);
        Mockito.when(alunoRepositorio.findByRg(Mockito.anyString())).thenReturn(Optional.of(aluno));

        String deficiencia = "Physical disability";

        assertThrows(AlreadyRegisteredException.class, () -> {
            alunoServices.adicionarDeficiencia("123456212", deficiencia);
        });
    }

    @Test
    void deveAdicionarUmAcompanhamentoNoHistoricoDeSaude() {
        HistoricoSaude hs = new HistoricoSaude(TipoSanguineo.AB_POSITIVO, FatorRH.POSITIVO,
                new UsoMedicamentoContinuo(false, "tipo", "atendimento"), null, null, null, List.of("mancamento"),
                List.of("atendimento"));

        aluno.setHistoricoSaude(hs);

        String rg = "123456789";
        String acompanhamentoSaude = "Acompanhamento 1";

        when(alunoRepositorio.findByRg(rg)).thenReturn(Optional.of(aluno));

        String result = alunoServices.adicionarAcompanhamentoSaude(rg, acompanhamentoSaude);

        assertEquals(acompanhamentoSaude, result);
    }

    @Test
    void adicionarNovoValorNoHistoricoDeSaudeDeficiencia() {
        String rg = "123456789";
        String deficiencia = "Deficiencia1";
        when(alunoRepositorio.findByRg(rg)).thenReturn(Optional.of(aluno));
        String result = alunoServices.adicionarDeficiencia(rg, deficiencia);
        assertEquals(result, deficiencia);
    }

    @Test
    void deveRemoverADeficienciaDoObjetoHistoricoDeSaude() {
        // Arrange
        aluno.setRg("123456789");
        aluno.setHistoricoSaude(new HistoricoSaude());
        aluno.getHistoricoSaude().setDeficiencias(List.of("Deficiencia1", "Deficiencia2", "Deficiencia3"));
        when(alunoRepositorio.findByRg(Mockito.anyString())).thenReturn(Optional.of(aluno));

        // Act
        String removedDeficiencia = alunoServices.removerDeficiencia("123456789", "Deficiencia2");

        // Assert
        assertEquals("Deficiencia2", removedDeficiencia);
    }
}