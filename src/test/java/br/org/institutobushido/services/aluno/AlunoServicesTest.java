package br.org.institutobushido.services.aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

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
import br.org.institutobushido.dtos.aluno.objects.dados_escolares.DadosEscolaresDTORequest;
import br.org.institutobushido.dtos.aluno.objects.dados_escolares.DadosEscolaresDTOResponse;
import br.org.institutobushido.dtos.aluno.objects.dados_sociais.DadosSociaisDTORequest;
import br.org.institutobushido.dtos.aluno.objects.dados_sociais.DadosSociaisDTOResponse;
import br.org.institutobushido.dtos.aluno.objects.endereco.EnderecoDTORequest;
import br.org.institutobushido.dtos.aluno.objects.endereco.EnderecoDTOResponse;
import br.org.institutobushido.dtos.aluno.objects.graduacao.GraduacaoDTORequest;
import br.org.institutobushido.dtos.aluno.objects.graduacao.GraduacaoDTOResponse;
import br.org.institutobushido.dtos.aluno.objects.responsavel.ResponsavelDTORequest;
import br.org.institutobushido.dtos.aluno.objects.responsavel.ResponsavelDTOResponse;
import br.org.institutobushido.enums.FiliacaoResposavel;
import br.org.institutobushido.enums.Turno;
import br.org.institutobushido.model.aluno.Aluno;
import br.org.institutobushido.model.aluno.object.DadosEscolares;
import br.org.institutobushido.model.aluno.object.DadosSociais;
import br.org.institutobushido.model.aluno.object.Endereco;
import br.org.institutobushido.model.aluno.object.Faltas;
import br.org.institutobushido.model.aluno.object.Graduacao;
import br.org.institutobushido.model.aluno.object.Responsavel;
import br.org.institutobushido.repositories.AlunoRepositorio;

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
                .build();

        aluno.setNome(alunoDtoRequest.nome());
        aluno.setDadosSociais(new DadosSociais());
        aluno.setDadosEscolares(new DadosEscolares());
        aluno.setRg(alunoDtoRequest.rg());
        aluno.setGraduacao(new Graduacao(5,new ArrayList<Faltas>(),false,75));
        aluno.setResponsaveis(responsaveis);
        aluno.setEndereco(new Endereco());

        reset(alunoRepositorio);
    }

    @Mock
    private AlunoRepositorio alunoRepositorio;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private AlunoServices alunoServices;

    @Test
    void deveRetornarTrueParaMetodoSaveForChamado() {;
        // Arrange
        when(alunoRepositorio.save(Mockito.any(Aluno.class))).thenReturn(aluno);

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
                .build();

        // Assert
        assertNotNull(result);
        assertEquals(alunoDtoResponse.dadosEscolares(), result.dadosEscolares());
        assertEquals(alunoDtoResponse.dadosSociais(), result.dadosSociais());
        assertEquals(alunoDtoResponse.dataPreenchimento(), result.dataPreenchimento());
    }

    @Test
    void deveConfirmarAsIntanciasDosValores() {
        when(alunoRepositorio.save(Mockito.any(Aluno.class))).thenReturn(aluno);
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

        System.out.println(result);

        assertEquals(aluno, result);
    }

    @Test
    void deveRetornarExceptionSeAlunoNaoForEncontrado() {
        AlunoRepositorio alunoRepositorio = mock(AlunoRepositorio.class);
        when(alunoRepositorio.findByRg(Mockito.anyString())).thenReturn(Optional.empty());
        assertThrows(MongoException.class, () -> {
            alunoServices.encontrarAlunoPorRg("nonexistent_rg");
        });
    }

    @Test
    void deveRetornarExceptionSeRgDoAlunoForPassadoComoNull() {
        AlunoServices alunoServices = new AlunoServices();

        assertThrows(NullPointerException.class, () -> {
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
        boolean result = alunoServices.removerResponsavel(rg, cpf);

        // Assert
        assertTrue(result);
    }

}