package br.org.institutobushido.model.aluno;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import br.org.institutobushido.enums.Imovel;
import br.org.institutobushido.enums.TipoDeTransporte;
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
    private boolean bolsaFamilia = false;
    private boolean auxilioBrasil = false;
    private Imovel imovel;
    private int numerosDePessoasNaCasa;
    private int contribuintesDaRendaFamiliar;
    private boolean alunoContribuiParaRenda = false;
    private int rendaFamiliarEmSalariosMinimos;
    private TipoDeTransporte transporte;
    private boolean vemAcompanhado = true;
    private Turno turno;
    private Date dataPreenchimento;
    private String cidade;
    private String estado;

    @Indexed(unique = true, background = true)
    private String rg;

    private List<Responsavel> responsaveis = new ArrayList<>();
    private int faltas = 0;
    private boolean active = true;

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

    public boolean isStatus() {
        if (getFaltas() >= 5) {
            this.setActive(false);
            return false;
        }
        return true;
    }
}
