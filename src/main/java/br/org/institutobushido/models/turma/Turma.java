package br.org.institutobushido.models.turma;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;

@Getter
@Document(collection = "turmas")
public class Turma {

    private String endereco;

    @Indexed(unique = true, background = true)
    private String nome;

    private String tutor;

    public Turma(String endereco, String nome, String tutor) {
        this.endereco = endereco;
        this.nome = nome;
        this.tutor = tutor;
    }

}
