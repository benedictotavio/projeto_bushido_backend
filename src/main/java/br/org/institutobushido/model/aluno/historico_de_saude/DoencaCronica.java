package br.org.institutobushido.model.aluno.historico_de_saude;

import br.org.institutobushido.abstracts.InformacaoSaude;

public class DoencaCronica extends InformacaoSaude {
    public DoencaCronica(boolean resposta, String tipo) {
        super(resposta, tipo);
    }
}