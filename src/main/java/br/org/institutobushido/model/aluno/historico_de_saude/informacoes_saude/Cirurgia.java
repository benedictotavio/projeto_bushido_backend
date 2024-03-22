package br.org.institutobushido.model.aluno.historico_de_saude.informacoes_saude;

import java.io.Serializable;

import br.org.institutobushido.abstracts.InformacaoSaude;

public class Cirurgia extends InformacaoSaude implements Serializable {

    private static final long serialVersionUID = 2405172041950251807L;

    public Cirurgia(boolean resposta, String tipo) {
        super(resposta, tipo);
    }
}