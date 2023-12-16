package br.org.institutobushido.model.aluno;

import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import br.org.institutobushido.enums.Imovel;
import br.org.institutobushido.enums.TransportType;
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
        aluno.setTransporte(TransportType.MOTO);
        aluno.setVemAcompanhado(false);
        aluno.setTurno(Turno.TARDE);
        aluno.setDataPreenchimento(new Date());
        aluno.setCidade("Example City");
        aluno.setEstado("Example State");
        aluno.setRg("123456789");
        aluno.setCpfResponsavel("987654321");
        aluno.setFaltas(2);
        aluno.setStatus(true);
    }

    @Test
    @DisplayName("Deve adicionar uma falta ao aluno")
    void deveAdicionarFaltaAoAluno() {
        int quantidadeTotalDeFaltasAtual = aluno.getFaltas();
        aluno.adicionarFalta();
        assertEquals(quantidadeTotalDeFaltasAtual + 1, aluno.getFaltas());
    }

    @Test
    @DisplayName("Não deve retirar uma falta do aluno")
    void nãoDeveRetirarFaltaAoAluno() {
        aluno.setFaltas(0);
        aluno.retiraFalta();
        assertEquals(0, aluno.getFaltas());
    }

    @Test
    @DisplayName("Deve retirar uma falta do aluno")
    void deveRetirarFaltaAoAluno() {
        aluno.setFaltas(5);
        int quantidadeTotalDeFaltasAtual = aluno.getFaltas();
        aluno.retiraFalta();
        assertEquals(quantidadeTotalDeFaltasAtual - 1, aluno.getFaltas());
    }

    @Test
    @DisplayName("Deve alterar o status se o aluno tem mais de 5 faltas")
    void deveAlterarStatusSeTiverMaisDeCincoFaltas() {
        // Arrange
        aluno.setStatus(true);
        aluno.setFaltas(5);

        // Act
        aluno.checarStatus();

        // Assert
        assertFalse(aluno.isStatus());
    }
}