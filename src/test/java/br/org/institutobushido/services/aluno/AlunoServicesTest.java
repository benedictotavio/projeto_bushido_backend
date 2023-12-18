package br.org.institutobushido.services.aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.institutobushido.enums.Imovel;
import br.org.institutobushido.enums.TransportType;
import br.org.institutobushido.enums.Turno;
import br.org.institutobushido.model.aluno.Aluno;
import br.org.institutobushido.repositories.AlunoRepositorio;

@SpringBootTest
class AlunoServicesTest {
    private Aluno aluno;

    @BeforeEach
    void setUp() {
        aluno = new Aluno();
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
        Aluno result = alunoServices.adicionarAluno(aluno);

        // Assert
        assertNotNull(result);
        assertEquals(aluno, result);
        verify(alunoRepositorio, times(1)).save(aluno);
    }

    @Test
    void deveConfirmarAsIntanciasDosValores() {
        aluno.setNome("João Lucas");
        aluno.setBolsaFamilia(true);
        aluno.setAuxilioBrasil(false);
        aluno.setImovel(Imovel.CEDIDO);
        aluno.setNumerosDePessoasNaCasa(4);
        aluno.setContribuintesDaRendaFamiliar(2);
        aluno.setAlunoContribuiParaRenda(true);
        aluno.setRendaFamiliarEmSalariosMinimos(2);
        aluno.setTransporte(TransportType.MOTO);
        aluno.setVemAcompanhado(false);
        aluno.setTurno(Turno.TARDE);
        aluno.setDataPreenchimento(new Date());
        aluno.setCidade("Example City");
        aluno.setEstado("Example State");
        aluno.setRg("123456789");
        aluno.setCpfResponsavel("987654321");
        aluno.setFaltas(5);
        aluno.setStatus(true);

        when(alunoRepositorio.save(aluno)).thenReturn(aluno);

        Aluno result = alunoServices.adicionarAluno(aluno);

        assertEquals(aluno.getNome(), result.getNome());
        assertEquals(aluno.isBolsaFamilia(), result.isBolsaFamilia());
        assertEquals(aluno.getImovel(), result.getImovel());
        assertEquals(aluno.getRendaFamiliarEmSalariosMinimos(), result.getRendaFamiliarEmSalariosMinimos());
        assertEquals(aluno.getDataPreenchimento(), result.getDataPreenchimento());
        assertEquals(aluno.isStatus(), result.isStatus());
        assertEquals(aluno.getFaltas(), result.getFaltas());
        assertEquals(aluno.getCpfResponsavel(), result.getCpfResponsavel());
    }

}
