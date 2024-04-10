package br.org.institutobushido.models.aluno.graduacao;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import br.org.institutobushido.models.aluno.graduacao.falta.Falta;
import br.org.institutobushido.resources.exceptions.AlreadyRegisteredException;
import br.org.institutobushido.resources.exceptions.EntityNotFoundException;
import br.org.institutobushido.resources.exceptions.InactiveUserException;
import br.org.institutobushido.resources.exceptions.LimitQuantityException;
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

    public Graduacao(int kyu, int dan) {

        if (kyu < 1 || kyu > 7) {
            throw new IllegalArgumentException("O kyu deve estar entre 1 a 7");
        }

        if (kyu == 1) {
            this.kyu = 1;
            this.frequencia = 100;
            this.faltas = new ArrayList<>();
            this.status = true;
            this.inicioGraduacao = LocalDate.now();
            this.fimGraduacao = LocalDate.now().plusMonths(4);
            this.aprovado = false;
            this.cargaHoraria = 0;
            this.dan = dan > 0 ? dan : 1;
            return;
        }

        this.kyu = kyu;
        this.dan = 1;
        this.frequencia = 100;
        this.faltas = new ArrayList<>();
        this.status = true;
        this.inicioGraduacao = LocalDate.now();
        this.fimGraduacao = LocalDate.now().plusMonths(4);
        this.aprovado = false;
        this.cargaHoraria = 0;

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

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setCargaHoraria(int cargaHoraria) {
        if (cargaHoraria < 0) {
            throw new LimitQuantityException("A carga horária deve ser maior que zero");
        }
        this.cargaHoraria = cargaHoraria;
    }

    public void setInicioGraduacao(LocalDate inicioGraduacao) {
        this.inicioGraduacao = inicioGraduacao;
    }

    public Falta adicionarFalta(String motivo, String observacao, long data) {

        if (!this.status) {
            throw new InactiveUserException("O Aluno esta inativo. Pois o mesmo se encontra com mais de 5 faltas");
        }

        if (data < this.getInicioGraduacao().atStartOfDay()
                .toInstant(ZoneOffset.UTC).toEpochMilli()) {
            throw new LimitQuantityException("A data deve ser maior ou igual a data de inicio da graduacao");
        }

        Falta novaFalta = new Falta(motivo, observacao, new Date(data));

        boolean faltaEstaRegistrada = this.getFaltas().stream()
                .anyMatch(falta -> falta.getData().equals(novaFalta.getData()));

        if (faltaEstaRegistrada) {
            throw new AlreadyRegisteredException("Ja existe uma falta para esta data");
        }

        this.faltas.add(novaFalta);
        return novaFalta;
    }

    public Falta removerFalta(String dataFalta) {
        Falta falta = this.getFaltas().stream().filter(f -> f.getData().equals(dataFalta)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Falta no dia " + dataFalta + " foi não encontrada"));

        this.getFaltas().remove(falta);
        return falta;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }

    public Graduacao aprovacao() {
        this.fimGraduacao = LocalDate.now();
        // if (this.kyu == 1) {
        //     setDan(this.dan + 1);
        // } else {
        //     setKyu(this.kyu - 1);
        // }
        setStatus(false);
        setAprovado(true);
        setCargaHoraria(definirCargaHoraria());
        setFrequencia(definirFrequencia());
        return this;
    }

    public void reprovacao() {
        this.fimGraduacao = LocalDate.now();
        setStatus(false);
        setAprovado(false);
        setCargaHoraria(definirCargaHoraria());
        setFrequencia(definirFrequencia());
    }

    private int definirFrequencia() {
        long weeksBetween = ChronoUnit.WEEKS.between(this.inicioGraduacao, this.fimGraduacao);
        if (weeksBetween <= 0) {
            throw new LimitQuantityException("O periodo de graduação é muito curto");
        }
        long totalHoursInGraduation = (weeksBetween * 3);
        this.frequencia = (int) ((this.cargaHoraria * 100) / totalHoursInGraduation);
        return this.frequencia;
    }

    private int definirCargaHoraria() {
        long weeksBetween = ChronoUnit.WEEKS.between(this.inicioGraduacao, this.fimGraduacao);
        if (weeksBetween <= 0) {
            throw new LimitQuantityException("O periodo de graduação é muito curto");
        }
        return (int) ((weeksBetween * 3) - (this.faltas.size() * 1));
    }

    @Override
    public String toString() {
        return "Graduacao [kyu=" + kyu + ", faltas=" + faltas + ", status=" + status + ", frequencia=" + frequencia
                + ", inicioGraduacao=" + inicioGraduacao + ", fimGraduacao=" + fimGraduacao + ", aprovado=" + aprovado
                + ", cargaHoraria=" + cargaHoraria + ", dan=" + dan + "]";
    }

}
