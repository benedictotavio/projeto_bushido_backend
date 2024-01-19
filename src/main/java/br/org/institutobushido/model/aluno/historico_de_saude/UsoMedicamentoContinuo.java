package br.org.institutobushido.model.aluno.historico_de_saude;

import br.org.institutobushido.abstracts.InformacaoSaude;

public class UsoMedicamentoContinuo extends InformacaoSaude {
    public UsoMedicamentoContinuo(boolean resposta, String tipo, String qualMedicamento) {
        super(resposta, tipo);
        this.qualMedicamento = qualMedicamento;
    }

    private String qualMedicamento;

    public String getQualMedicamento() {
        return this.qualMedicamento;
    }

    public void setQualMedicamento(String qualMedicamento) {
        this.qualMedicamento = qualMedicamento;
    }
}