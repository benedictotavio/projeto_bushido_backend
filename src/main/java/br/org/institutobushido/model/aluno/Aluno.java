package br.org.institutobushido.model.aluno;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import br.org.institutobushido.enums.Imovel;
import br.org.institutobushido.enums.TransportType;
import br.org.institutobushido.enums.Turno;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "alunos")
public class Aluno implements AlunoInterface {

    private String nome;
    private boolean bolsaFamilia;
    private boolean auxilioBrasil;
    private Imovel imovel;
    private int numerosDePessoasNaCasa;
    private int contribuintesDaRendaFamiliar;
    private boolean alunoContribuiParaRenda;
    private int rendaFamiliarEmSalariosMinimos;
    private TransportType transporte;
    private boolean vemAcompanhado;
    private Turno turno;
    private Date dataPreenchimento;
    private String cidade;
    private String estado;
    private String rg;
    private String cpfResponsavel;
    private int faltas;
    private boolean status;

    @Override
    public void adicionarFalta() {
        this.faltas = (getFaltas() + 1);
    }

    @Override
    public void retiraFalta() {
        if (this.faltas > 0) {
            this.faltas = getFaltas() - 1;
        }
    }

    @Override
    public boolean checarStatus() {
        if (this.getFaltas() >= 5) {
            this.setStatus(false);
            return false;
        }
        return true;
    }
}
