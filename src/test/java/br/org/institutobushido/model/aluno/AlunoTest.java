package br.org.institutobushido.model.aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import br.org.institutobushido.enums.Imovel;
import br.org.institutobushido.enums.TipoDeTransporte;
import br.org.institutobushido.enums.Turno;

@SpringBootTest
class AlunoTest {
    private Aluno aluno;

    @BeforeEach
    void setUp() {
        aluno = new Aluno();
        aluno.setNome("João Lucas");
        aluno.setBolsaFamilia(true);
        aluno.setAuxilioBrasil(false);
        aluno.setImovel(Imovel.CEDIDO);
        aluno.setNumerosDePessoasNaCasa(4);
        aluno.setContribuintesDaRendaFamiliar(2);
        aluno.setAlunoContribuiParaRenda(true);
        aluno.setRendaFamiliarEmSalariosMinimos(2);
        aluno.setTransporte(TipoDeTransporte.MOTO);
        aluno.setVemAcompanhado(false);
        aluno.setTurno(Turno.TARDE);
        aluno.setDataPreenchimento(new Date());
        aluno.setCidade("Example City");
        aluno.setEstado("Example State");
        aluno.setRg("123456789");
        aluno.setFaltas(2);
        aluno.setActive(true);
        ;
    }

    @Test
    void deveAdicionarFaltaAoAluno() {
        int quantidadeTotalDeFaltasAtual = aluno.getFaltas();
        aluno.adicionarFalta();
        assertEquals(quantidadeTotalDeFaltasAtual + 1, aluno.getFaltas());
    }

    @Test
    void nãoDeveRetirarFaltaAoAluno() {
        aluno.setFaltas(0);
        aluno.retiraFalta();
        assertEquals(0, aluno.getFaltas());
    }

    @Test
    void deveRetirarFaltaAoAluno() {
        aluno.setFaltas(5);
        int quantidadeTotalDeFaltasAtual = aluno.getFaltas();
        aluno.retiraFalta();
        assertEquals(quantidadeTotalDeFaltasAtual - 1, aluno.getFaltas());
    }

    @Test
    void deveAlterarStatusSeTiverMaisDeCincoFaltas() {
        // Arrange
        aluno.setFaltas(5);

        // Act
        aluno.isStatus();

        // Assert
        assertFalse(aluno.isActive());
    }
}
