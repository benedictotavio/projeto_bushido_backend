package br.org.institutobushido.model.aluno.graduacao;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import br.org.institutobushido.model.aluno.graduacao.falta.Falta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Graduacao implements Serializable {

    private static final long serialVersionUID = 2405172041950251807L;

    private int kyu;
    private List<Falta> faltas;
    private boolean status;
    private int frequencia;
    LocalDate inicioGraduacao;
    LocalDate fimGraduacao;
    boolean aprovado;
    int cargaHoraria;
    int dan;

    public Graduacao(int kyu) {
        if (kyu <= 1) {
            this.kyu = 1;
            this.frequencia = 100;
            this.faltas = new ArrayList<>();
            this.status = true;
            this.inicioGraduacao = LocalDate.now();
            this.fimGraduacao = LocalDate.now().plusMonths(4);
            this.aprovado = false;
            this.cargaHoraria = 0;
        } else {
            this.kyu = kyu;
            this.dan = 0;
            this.frequencia = 100;
            this.faltas = new ArrayList<>();
            this.status = true;
            this.inicioGraduacao = LocalDate.now();
            this.fimGraduacao = LocalDate.now().plusMonths(4);
            this.aprovado = false;
            this.cargaHoraria = 0;
        }
    }

    public void setDan(int dan) {
        this.dan = dan;
    }

    public void setKyu(int kyu) {
        this.kyu = kyu;
    }

    public void setFrequencia(int frequencia) {
        this.frequencia = frequencia;
    }

    public void setFaltas(List<Falta> faltas) {
        this.faltas = faltas;
    }

    public void finalizarGraduacao() {
        this.fimGraduacao = LocalDate.now();
    }

    public Falta adicionarFaltas(Falta falta) {
        this.faltas.add(falta);
        return falta;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }

    public void aprovacao() {
        this.fimGraduacao = LocalDate.now();
        setAprovado(true);
    }

    @Override
    public String toString() {
        return "Graduacao [kyu=" + kyu + ", faltas=" + faltas + ", status=" + status + ", frequencia=" + frequencia
                + ", inicioGraduacao=" + inicioGraduacao + ", fimGraduacao=" + fimGraduacao + ", aprovado=" + aprovado
                + ", cargaHoraria=" + cargaHoraria + "]";
    }

    // TODO: Definir lógica para definir frequência
    // public int definirFrequencia(){
    // return this.frequencia;
    // }

}
