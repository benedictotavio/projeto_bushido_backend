package br.org.institutobushido.models.aluno.dados_sociais;

import java.io.Serializable;

import br.org.institutobushido.providers.enums.aluno.Imovel;
import br.org.institutobushido.providers.utils.resources.exceptions.LimitQuantityException;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DadosSociais implements Serializable {
    private static final long serialVersionUID = 2405172041950251807L;

    private Imovel imovel;
    private int numerosDePessoasNaCasa;
    private int contribuintesDaRendaFamiliar;
    private int rendaFamiliar;
    private boolean alunoContribuiParaRenda;
    private boolean bolsaFamilia;
    private boolean auxilioBrasil;

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
}
