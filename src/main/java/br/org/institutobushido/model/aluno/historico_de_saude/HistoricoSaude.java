package br.org.institutobushido.model.aluno.historico_de_saude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.org.institutobushido.enums.aluno.FatorRH;
import br.org.institutobushido.enums.aluno.TipoSanguineo;
import br.org.institutobushido.model.aluno.historico_de_saude.informacoes_saude.Alergia;
import br.org.institutobushido.model.aluno.historico_de_saude.informacoes_saude.Cirurgia;
import br.org.institutobushido.model.aluno.historico_de_saude.informacoes_saude.DoencaCronica;
import br.org.institutobushido.model.aluno.historico_de_saude.informacoes_saude.UsoMedicamentoContinuo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoSaude implements Serializable {

    private static final long serialVersionUID = 2405172041950251807L;

    private TipoSanguineo tipoSanguineo;
    private FatorRH fatorRh;
    private UsoMedicamentoContinuo usoMedicamentoContinuo;
    private DoencaCronica doencaCronica;
    private Alergia alergia;
    private Cirurgia cirurgia;
    private List<String> deficiencias = new ArrayList<>();
    private List<String> acompanhamentoSaude = new ArrayList<>();
    
    public void setUsoMedicamentoContinuo(UsoMedicamentoContinuo usoMedicamentoContinuo) {
        if (usoMedicamentoContinuo == null) {
            return;
        }
        this.usoMedicamentoContinuo = usoMedicamentoContinuo;
    }
    public void setDoencaCronica(DoencaCronica doencaCronica) {
        if (doencaCronica == null) {
            return;
        }
        this.doencaCronica = doencaCronica;
    }
    public void setAlergia(Alergia alergia) {
        if (alergia == null) {
            return;
        }
        this.alergia = alergia;
    }
    public void setCirurgia(Cirurgia cirurgia) {
        if (cirurgia == null) {
            return;
        }
        this.cirurgia = cirurgia;
    }
    @Override
    public String toString() {
        return "HistoricoSaude [tipoSanguineo=" + tipoSanguineo + ", fatorRh=" + fatorRh + ", usoMedicamentoContinuo="
                + usoMedicamentoContinuo + ", doencaCronica=" + doencaCronica + ", alergia=" + alergia + ", cirurgia="
                + cirurgia + ", deficiencias=" + deficiencias + ", acompanhamentoSaude=" + acompanhamentoSaude + "]";
    }
}