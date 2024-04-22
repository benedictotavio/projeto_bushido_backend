package br.org.institutobushido.models.admin.turmas;

import java.io.Serializable;
import lombok.Getter;

@Getter
public class TurmaResponsavel implements Serializable {
    private static final long serialVersionUID = 2405172041950251807L;

    private String endereco;
    private String nome;

    public TurmaResponsavel(String endereco, String nome) {
        this.endereco = endereco;
        this.nome = nome;
    }
}
