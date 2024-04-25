package br.org.institutobushido.models.turma;

import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import br.org.institutobushido.models.turma.tutor.Tutor;
import lombok.Getter;

@Getter
@Document(collection = "turmas")
public class Turma{

    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    private String endereco;

    @Indexed(unique = true, background = true)
    private String nome;

    private Tutor tutor;

    private LocalDate dataCriacao;

    public Turma(String endereco, String nome, Tutor tutor) {
        this.endereco = endereco;
        this.nome = nome;
        this.tutor = tutor;
        this.dataCriacao = LocalDate.now(ZONE_ID);
    }

    public void setTutor(Tutor tutor) {
        if (tutor == null) {
            return;
        }
        this.tutor = tutor;
    }
}
