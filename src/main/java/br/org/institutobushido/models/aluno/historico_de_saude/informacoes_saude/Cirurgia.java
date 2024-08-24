package br.org.institutobushido.models.aluno.historico_de_saude.informacoes_saude;

import java.io.Serializable;

import br.org.institutobushido.providers.abstracts.InformacaoSaude;

public class Cirurgia extends InformacaoSaude implements Serializable {

    private static final long serialVersionUID = 2405172041950251807L;

    public Cirurgia(String tipo) {
        super(tipo);
    }
}