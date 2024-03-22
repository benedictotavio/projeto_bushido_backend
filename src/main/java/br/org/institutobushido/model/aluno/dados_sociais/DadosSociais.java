package br.org.institutobushido.model.aluno.dados_sociais;

import java.io.Serializable;

import br.org.institutobushido.enums.aluno.Imovel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DadosSociais implements Serializable {
    private static final long serialVersionUID = 2405172041950251807L;

    private boolean bolsaFamilia = false;
    private boolean auxilioBrasil = false;
    private Imovel imovel;
    private int numerosDePessoasNaCasa = 1;
    private int contribuintesDaRendaFamiliar = 1;
    private boolean alunoContribuiParaRenda = false;
    private int rendaFamiliarEmSalariosMinimos;

    public void setBolsaFamilia(boolean bolsaFamilia) {
        this.bolsaFamilia = bolsaFamilia;
    }

    public void setAuxilioBrasil(boolean auxilioBrasil) {
        this.auxilioBrasil = auxilioBrasil;
    }

    public void setImovel(Imovel imovel) {
        if (imovel == null) {
            imovel = this.imovel;
        }
        this.imovel = imovel;
    }

    public void setNumerosDePessoasNaCasa(int numerosDePessoasNaCasa) {
        this.numerosDePessoasNaCasa = numerosDePessoasNaCasa;
    }

    public void setContribuintesDaRendaFamiliar(int contribuintesDaRendaFamiliar) {
        this.contribuintesDaRendaFamiliar = contribuintesDaRendaFamiliar;
    }

    public void setAlunoContribuiParaRenda(boolean alunoContribuiParaRenda) {
        this.alunoContribuiParaRenda = alunoContribuiParaRenda;
    }

    public void setRendaFamiliarEmSalariosMinimos(int rendaFamiliarEmSalariosMinimos) {
        this.rendaFamiliarEmSalariosMinimos = rendaFamiliarEmSalariosMinimos;
    }

}
