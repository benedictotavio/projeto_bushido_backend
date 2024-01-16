package br.org.institutobushido.model.aluno.object;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Graduacao {

    

    public Graduacao(int kyu, int frequencia) {
        this.kyu = kyu;
        this.frequencia = frequencia;
    }

    private int kyu;
    private List<Faltas> faltas = new ArrayList<>();
    private boolean status = true;
    private int frequencia;
}
