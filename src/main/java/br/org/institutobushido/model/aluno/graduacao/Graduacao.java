package br.org.institutobushido.model.aluno.graduacao;

import java.util.ArrayList;
import java.util.List;

import br.org.institutobushido.model.aluno.graduacao.falta.Falta;
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
    private List<Falta> faltas = new ArrayList<>();
    private boolean status = true;
    private int frequencia;
}
