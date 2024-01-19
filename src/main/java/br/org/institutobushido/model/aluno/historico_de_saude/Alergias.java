package br.org.institutobushido.model.aluno.historico_de_saude;

import br.org.institutobushido.abstracts.InformacaoSaude;

public class Alergias extends InformacaoSaude {
    public Alergias(boolean resposta, String tipo) {
        super(resposta, tipo);
    }
}