package br.org.institutobushido.services.aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.institutobushido.dtos.aluno.AlunoDTORequest;
import br.org.institutobushido.dtos.aluno.AlunoDTOResponse;
import br.org.institutobushido.enums.Imovel;
import br.org.institutobushido.enums.TipoDeTransporte;
import br.org.institutobushido.enums.Turno;
import br.org.institutobushido.model.aluno.Aluno;
import br.org.institutobushido.repositories.AlunoRepositorio;

@SpringBootTest
class AlunoServicesTest {

    private Aluno aluno;
    private AlunoDTORequest alunoDtoRequest;
    private AlunoDTOResponse alunoDtoResponse;

    @BeforeEach
    void setUp() {
        aluno = new Aluno();

        alunoDtoRequest = new AlunoDTORequest(
                "João Algo",
                true,
                false,
                Imovel.CEDIDO,
                4,
                2,
                true,
                3,
                TipoDeTransporte.ONIBUS,
                false,
                Turno.NOITE,
                new Date(),
                "CidadeTeste",
                "EstadoTeste",
                "123456789",
                "98765432100",
                2,
                false);

        aluno.setNome(alunoDtoRequest.nome());
        aluno.setBolsaFamilia(alunoDtoRequest.bolsaFamilia());
        aluno.setImovel(alunoDtoRequest.imovel());
        aluno.setAuxilioBrasil(alunoDtoRequest.auxilioBrasil());
        aluno.setNumerosDePessoasNaCasa(alunoDtoRequest.numerosDePessoasNaCasa());
        aluno.setCidade(alunoDtoRequest.cidade());
        aluno.setCpfResponsavel(alunoDtoRequest.cpfResponsavel());
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

    }

    @Mock
    private AlunoRepositorio alunoRepositorio;

    @InjectMocks
    private AlunoServices alunoServices;

    @Test
    void deveRetornarTrueParaMetodoSaveForChamado() {
        // Arrange
        when(alunoRepositorio.save(aluno)).thenReturn(aluno);

        // Act
        AlunoDTOResponse result = alunoServices.adicionarAluno(alunoDtoRequest);

        alunoDtoResponse = new AlunoDTOResponse(aluno.getNome(), aluno.isBolsaFamilia(), aluno.isAuxilioBrasil(),
                aluno.getImovel(), aluno.getNumerosDePessoasNaCasa(),
                aluno.getContribuintesDaRendaFamiliar(), aluno.isAlunoContribuiParaRenda(),
                aluno.getRendaFamiliarEmSalariosMinimos(), aluno.getTransporte(), aluno.isVemAcompanhado(),
                aluno.getTurno(), aluno.getDataPreenchimento(),
                aluno.getCidade(), aluno.getEstado(), aluno.getRg(), aluno.getCpfResponsavel(),
                aluno.getFaltas(), aluno.isActive());

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
        assertEquals(aluno.getCpfResponsavel(), result.cpfResponsavel());
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
}