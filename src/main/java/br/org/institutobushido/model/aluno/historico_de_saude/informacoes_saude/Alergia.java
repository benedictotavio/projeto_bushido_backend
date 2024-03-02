package br.org.institutobushido.model.aluno.historico_de_saude.informacoes_saude;

import br.org.institutobushido.abstracts.InformacaoSaude;

public class Alergia extends InformacaoSaude {
    public Alergia(boolean resposta, String tipo) {
        super(resposta, tipo);
    }
}