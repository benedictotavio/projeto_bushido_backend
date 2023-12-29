package br.org.institutobushido.services.aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
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
import br.org.institutobushido.dtos.aluno.ResponsavelDTORequest;
import br.org.institutobushido.dtos.aluno.ResponsavelDTOResponse;
import br.org.institutobushido.enums.FiliacaoResposavel;
import br.org.institutobushido.enums.Imovel;
import br.org.institutobushido.enums.TipoDeTransporte;
import br.org.institutobushido.enums.Turno;
import br.org.institutobushido.model.aluno.Aluno;
import br.org.institutobushido.model.aluno.Responsavel;
import br.org.institutobushido.repositories.AlunoRepositorio;

@SpringBootTest
class AlunoServicesTest {

    private Aluno aluno;
    private List<Responsavel> responsaveis = new ArrayList<>();
    private List<ResponsavelDTORequest> responsaveisDTORequest = new ArrayList<>();
    private AlunoDTORequest alunoDtoRequest;
    private AlunoDTOResponse alunoDtoResponse;

    @BeforeEach
    void setUp() {
        aluno = new Aluno();

        alunoDtoRequest = AlunoDTORequest.builder()
                .withNome("João Algo")
                .withBolsaFamilia(true)
                .withAuxilioBrasil(false)
                .withImovel(Imovel.CEDIDO)
                .withNumerosDePessoasNaCasa(4)
                .withContribuintesDaRendaFamiliar(2)
                .withAlunoContribuiParaRenda(true)
                .withRendaFamiliarEmSalariosMinimos(3)
                .withTransporte(TipoDeTransporte.ONIBUS)
                .withVemAcompanhado(false)
                .withTurno(Turno.NOITE)
                .withDataPreenchimento(new Date())
                .withCidade("CidadeTeste")
                .withEstado("EstadoTeste")
                .withRg("123456789")
                .withResponsaveis(responsaveisDTORequest)
                .withFaltas(2)
                .withStatus(false)
                .build();

        aluno.setNome(alunoDtoRequest.nome());
        aluno.setBolsaFamilia(alunoDtoRequest.bolsaFamilia());
        aluno.setImovel(alunoDtoRequest.imovel());
        aluno.setAuxilioBrasil(alunoDtoRequest.auxilioBrasil());
        aluno.setNumerosDePessoasNaCasa(alunoDtoRequest.numerosDePessoasNaCasa());
        aluno.setCidade(alunoDtoRequest.cidade());
        aluno.setDataPreenchimento(alunoDtoRequest.dataPreenchimento());
        aluno.setContribuintesDaRendaFamiliar(alunoDtoRequest.contribuintesDaRendaFamiliar());
        aluno.setEstado(alunoDtoRequest.estado());
        aluno.setAlunoContribuiParaRenda(alunoDtoRequest.alunoContribuiParaRenda());
        aluno.setRendaFamiliarEmSalariosMinimos(alunoDtoRequest.rendaFamiliarEmSalariosMinimos());
        aluno.setTransporte(alunoDtoRequest.transporte());
        aluno.setVemAcompanhado(alunoDtoRequest.vemAcompanhado());
        aluno.setTurno(alunoDtoRequest.turno());
        aluno.setRg(alunoDtoRequest.rg());
        aluno.setFaltas(alunoDtoRequest.faltas());
        aluno.setActive(alunoDtoRequest.status());
        aluno.setResponsaveis(responsaveis);

        Mockito.reset(alunoRepositorio);
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
        when(alunoRepositorio.save(aluno)).thenReturn(aluno);

        // Act
        AlunoDTOResponse result = alunoServices.adicionarAluno(alunoDtoRequest);

        alunoDtoResponse = AlunoDTOResponse.builder()
                .withNome(aluno.getNome())
                .withBolsaFamilia(aluno.isBolsaFamilia())
                .withAuxilioBrasil(aluno.isAuxilioBrasil())
                .withImovel(aluno.getImovel())
                .withNumerosDePessoasNaCasa(aluno.getNumerosDePessoasNaCasa())
                .withContribuintesDaRendaFamiliar(aluno.getContribuintesDaRendaFamiliar())
                .withAlunoContribuiParaRenda(aluno.isAlunoContribuiParaRenda())
                .withRendaFamiliarEmSalariosMinimos(aluno.getRendaFamiliarEmSalariosMinimos())
                .withTransporte(aluno.getTransporte())
                .withVemAcompanhado(aluno.isVemAcompanhado())
                .withTurno(aluno.getTurno())
                .withDataPreenchimento(aluno.getDataPreenchimento())
                .withCidade(aluno.getCidade())
                .withEstado(aluno.getEstado())
                .withRg(aluno.getRg())
                .withResponsaveis(new ArrayList<>())
                .withFaltas(aluno.getFaltas())
                .withStatus(aluno.isActive())
                .build();

        // Assert
        assertNotNull(result);
        assertEquals(alunoDtoResponse, result);
        verify(alunoRepositorio, times(1)).save(aluno);
    }

    @Test
    void deveConfirmarAsIntanciasDosValores() {
        when(alunoRepositorio.save(aluno)).thenReturn(aluno);
        AlunoDTOResponse result = alunoServices.adicionarAluno(alunoDtoRequest);
        assertEquals(aluno.getNome(), result.nome());
        assertEquals(aluno.isBolsaFamilia(), result.bolsaFamilia());
        assertEquals(aluno.getImovel(), result.imovel());
        assertEquals(aluno.getRendaFamiliarEmSalariosMinimos(), result.rendaFamiliarEmSalariosMinimos());
        assertEquals(aluno.getDataPreenchimento(), result.dataPreenchimento());
        assertEquals(aluno.isActive(), result.status());
        assertEquals(aluno.getFaltas(), result.faltas());
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
        Mockito.when(alunoRepositorio.findByRg(rg)).thenReturn(Optional.of(aluno));

        Aluno result = alunoRepositorio.findByRg(rg)
                .orElseThrow(() -> new MongoException("Email: " + rg + " não encontrado"));

        System.out.println(result);

        assertEquals(aluno, result);
    }

    @Test
    void deveRetornarExceptionSeAlunoNaoForEncontrado() {
        AlunoRepositorio alunoRepositorio = mock(AlunoRepositorio.class);
        Mockito.when(alunoRepositorio.findByRg(Mockito.anyString())).thenReturn(Optional.empty());

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
    void deveAdicionarFalta() {
        // Arrange
        Optional<Aluno> alunoTest = Optional.of(aluno);
        String validRg = "123456789";
        when(alunoRepositorio.findByRg(validRg)).thenReturn(alunoTest);

        // Act
        int initialAbsences = alunoServices.adicionarFaltaDoAluno(validRg);
        int updatedAbsences = alunoServices.adicionarFaltaDoAluno(validRg);

        // Assert
        assertEquals(initialAbsences + 1, updatedAbsences);
    }

    @Test
    void deveRetirarFalta() {
        // Arrange
        Optional<Aluno> alunoTest = Optional.of(aluno);
        String validRg = "123456789";
        when(alunoRepositorio.findByRg(validRg)).thenReturn(alunoTest);
        // Act
        int result = alunoServices.retirarFaltaDoAluno(validRg);

        // Assert
        assertEquals(aluno.getFaltas(), result);
    }

    @Test
    void deveRetornarUmaExceçãoCaseEmailForVazio() {
        String emptyRg = "";

        // Act & Assert
        assertThrows(MongoException.class, () -> {
            alunoServices.adicionarFaltaDoAluno(emptyRg);
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

         // should remove a responsavel from an existing aluno
    @Test
    void test_remove_responsavel_successfully() {
        // Arrange
        String rg = "123456";
        String cpf = "987654";
        Optional<Aluno> alunoTest = Optional.of(aluno);
        when(alunoRepositorio.findByRg(rg)).thenReturn(alunoTest);
    
        // Mocking the aluno object
        aluno.setRg(rg);
        Responsavel responsavel = new Responsavel();
        responsavel.setCpf(cpf);
        aluno.getResponsaveis().add(responsavel);
    
        // Mocking the alunoRepositorio
        AlunoRepositorio alunoRepositorio = mock(AlunoRepositorio.class);
        when(alunoRepositorio.findByRg(rg)).thenReturn(Optional.of(aluno));

        // Act
        boolean result = alunoServices.removerResponsavel(rg, cpf);
    
        // Assert
        assertTrue(result);
    }

    
}