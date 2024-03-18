package br.org.institutobushido.model.aluno.graduacao;

import java.util.ArrayList;
import java.util.List;

import br.org.institutobushido.model.aluno.graduacao.falta.Falta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Graduacao {

    public Graduacao(int kyu, int frequencia) {
        this.kyu = kyu;
        this.frequencia = frequencia;
        this.faltas = new ArrayList<>();
        this.status = true;
    }

    private int kyu;
    private List<Falta> faltas;
    private boolean status;
    private int frequencia;

    public void setKyu(int kyu) {
        this.kyu = kyu;
    }
    public void setFrequencia(int frequencia) {
        this.frequencia = frequencia;
    }
    
}
