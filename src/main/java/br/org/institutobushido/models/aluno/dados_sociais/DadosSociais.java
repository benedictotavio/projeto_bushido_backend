package br.org.institutobushido.models.aluno.dados_sociais;

import java.io.Serializable;

import br.org.institutobushido.enums.aluno.Imovel;
import br.org.institutobushido.utils.resources.exceptions.LimitQuantityException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DadosSociais implements Serializable {
    private static final long serialVersionUID = 2405172041950251807L;

    private boolean bolsaFamilia;
    private boolean auxilioBrasil;
    private Imovel imovel;
    private int numerosDePessoasNaCasa;
    private int contribuintesDaRendaFamiliar;
    private boolean alunoContribuiParaRenda;
    private int rendaFamiliar;

    public DadosSociais(Imovel imovel, int numerosDePessoasNaCasa, int contribuintesDaRendaFamiliar,
            int rendaFamiliar) {

        if (numerosDePessoasNaCasa < 1) {
            throw new LimitQuantityException("A quantidade de pessoas deve ser maior que zero");
        }

        if (contribuintesDaRendaFamiliar > numerosDePessoasNaCasa) {
            throw new LimitQuantityException(
                    "A Quantidade de contribuintes deve ser menor ou igual a quantidade de pessoas na casa");
        }

        this.imovel = imovel;
        this.numerosDePessoasNaCasa = numerosDePessoasNaCasa;
        this.contribuintesDaRendaFamiliar = contribuintesDaRendaFamiliar;
        this.rendaFamiliar = rendaFamiliar;
        this.alunoContribuiParaRenda = false;
        this.bolsaFamilia = false;
        this.auxilioBrasil = false;
    }

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
        if (numerosDePessoasNaCasa < 1) {
            throw new LimitQuantityException("A quantidade de pessoas deve ser maior que zero");
        }
        this.numerosDePessoasNaCasa = numerosDePessoasNaCasa;
    }

    public void setContribuintesDaRendaFamiliar(int contribuintesDaRendaFamiliar) {
        if (contribuintesDaRendaFamiliar > this.getNumerosDePessoasNaCasa()) {
            throw new LimitQuantityException(
                    "A Quantidade de contribuintes deve ser menor ou igual a quantidade de pessoas na casa");
        }
        this.contribuintesDaRendaFamiliar = contribuintesDaRendaFamiliar;
    }

    public void setAlunoContribuiParaRenda(boolean alunoContribuiParaRenda) {
        this.alunoContribuiParaRenda = alunoContribuiParaRenda;
    }

    public void setRendaFamiliar(int rendaFamiliar) {
        this.rendaFamiliar = rendaFamiliar;
    }

}
