package br.org.institutobushido.model.turma;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "turma")
public class Turma {
    @Id
    @Indexed(unique = true, background = true)
    private UUID id;
    private String endereco;
    private String nome;
    private String tutor;

    public Turma(String endereco, String nome, String tutor) {
        this.id = UUID.randomUUID();
        this.endereco = endereco;
        this.nome = nome;
        this.tutor = tutor;
    }
    
}
