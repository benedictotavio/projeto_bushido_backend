package br.org.institutobushido.models.turma.tutor;

import lombok.Getter;

@Getter
public class Tutor {

    private String nome;
    private String email;

    public Tutor(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }
}
