package br.org.institutobushido.models.aluno.graduacao;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.org.institutobushido.models.aluno.graduacao.falta.Falta;
import br.org.institutobushido.utils.default_values.ValoresPadraoGraduacao;
import br.org.institutobushido.utils.resources.exceptions.AlreadyRegisteredException;
import br.org.institutobushido.utils.resources.exceptions.EntityNotFoundException;
import br.org.institutobushido.utils.resources.exceptions.InactiveUserException;
import br.org.institutobushido.utils.resources.exceptions.LimitQuantityException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
    int nota;

    public Graduacao(int kyu, int dan) {

        if (kyu > ValoresPadraoGraduacao.PRIMEIRO_KYU) {
            throw new LimitQuantityException("O kyu deve ser até 7");
        }

        if (kyu == 0 && dan == 0) {
            throw new LimitQuantityException("informe corretamente o kyu ou o dan");
        }

        if (kyu <= 0) {
            this.kyu = 0;
            this.frequencia = ValoresPadraoGraduacao.FREQUENCIA_TOTAL;
            this.faltas = new ArrayList<>();
            this.status = true;
            this.inicioGraduacao = LocalDate.now();
            this.fimGraduacao = LocalDate.now().plusMonths(4);
            this.aprovado = false;
            this.cargaHoraria = ValoresPadraoGraduacao.CARGA_HORARIA_INICIAL;
            this.dan = dan > 0 ? dan : 1;
            this.nota = 0;
            return;
        }

        this.kyu = kyu;
        this.dan = 0;
        this.frequencia = ValoresPadraoGraduacao.FREQUENCIA_TOTAL;
        this.faltas = new ArrayList<>();
        this.status = true;
        this.inicioGraduacao = LocalDate.now();
        this.fimGraduacao = LocalDate.now().plusMonths(4);
        this.aprovado = false;
        this.cargaHoraria = ValoresPadraoGraduacao.CARGA_HORARIA_INICIAL;
        this.nota = 0;
    }

    public void setNota(int nota) {
        if (nota < ValoresPadraoGraduacao.MENOR_NOTA_POSSIVEL || nota > ValoresPadraoGraduacao.MAIOR_NOTA_POSSIVEL) {
            throw new LimitQuantityException("A nota deve estar entre 0 e 10");
        }
        this.nota = nota;
    }

    public void setFimGraduacao(LocalDate fimGraduacao) {

        if (fimGraduacao.isBefore(this.inicioGraduacao)) {
            throw new LimitQuantityException("O fim da graduação deve ser maior que o inicio da graduação");
        }

        this.fimGraduacao = fimGraduacao;
    }

    public void setDan(int dan) {
        this.dan = dan;
    }

    public void setKyu(int kyu) {
        if (kyu < 0) {
            this.kyu = 0;
        }

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

        if (inicioGraduacao.isBefore(LocalDate.now())) {
            throw new LimitQuantityException("O inicio da graduação não pode ser menor que a data atual");
        }

        this.inicioGraduacao = inicioGraduacao;
    }

    public Falta adicionarFalta(String motivo, String observacao, long data) {

        if (!this.isStatus()) {
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

        if (this.definirQuantidadeSemanasNaGraduacao(this.getInicioGraduacao(),
                this.getFimGraduacao()) > ValoresPadraoGraduacao.NUMERO_MINIMO_DE_SEMANAS
                && this.getCargaHoraria() <= ValoresPadraoGraduacao.CARGA_HORARIA_INICIAL + 1) {
            throw new LimitQuantityException("Aulas insuficientes para o aluno");
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

    public Graduacao aprovacao(int notaDaProva) {

        if (!this.status) {
            throw new InactiveUserException("O Aluno esta inativo. Pois o mesmo se encontra com mais de 5 faltas");
        }

        if (notaDaProva < ValoresPadraoGraduacao.NOTA_MINIMA_APROVACAO) {
            throw new LimitQuantityException("Para aprovar o aluno, a nota deve ser maior que 6");
        }

        if (this.getCargaHoraria() < ValoresPadraoGraduacao.CARGA_HORARIA_MINIMA_PROVA) {
            throw new LimitQuantityException("Carga horaria insuficiente para realizar a prova.");
        }

        setFimGraduacao(LocalDate.now());
        setStatus(false);
        setAprovado(true);
        setCargaHoraria(definirCargaHoraria());
        setFrequencia(definirFrequencia());
        setNota(notaDaProva);
        return this;
    }

    public void reprovacao(int notaDaProva) {

        if (notaDaProva > ValoresPadraoGraduacao.NOTA_MINIMA_APROVACAO) {
            throw new LimitQuantityException("Para reprovar o aluno, a nota ser menor que 6");
        }

        if (this.getCargaHoraria() < ValoresPadraoGraduacao.CARGA_HORARIA_MINIMA_PROVA) {
            throw new LimitQuantityException("Carga horaria insuficiente para realizar a prova.");
        }

        setFimGraduacao(LocalDate.now());
        setStatus(false);
        setAprovado(false);
        setCargaHoraria(definirCargaHoraria());
        setFrequencia(definirFrequencia());
        setNota(notaDaProva);
    }

    public int definirFrequencia() {

        long quantidadeSemanasNaGraduacao = this.definirQuantidadeSemanasNaGraduacao(this.getInicioGraduacao(),
                this.getFimGraduacao());

        if (quantidadeSemanasNaGraduacao < ValoresPadraoGraduacao.NUMERO_MINIMO_DE_SEMANAS) {
            return ValoresPadraoGraduacao.FREQUENCIA_TOTAL;
        }

        long totalHoursInGraduation = (quantidadeSemanasNaGraduacao
                * ValoresPadraoGraduacao.CARGA_HORARIA_SEMANAL_HORAS);
        setFrequencia((int) ((this.cargaHoraria * 100) / totalHoursInGraduation));
        return this.frequencia;
    }

    public int definirCargaHoraria() {

        long quantidadeSemanasNaGraduacao = this.definirQuantidadeSemanasNaGraduacao(this.getInicioGraduacao(),
                this.getFimGraduacao());

        if (quantidadeSemanasNaGraduacao <= ValoresPadraoGraduacao.NUMERO_MINIMO_DE_SEMANAS) {
            return ValoresPadraoGraduacao.CARGA_HORARIA_INICIAL;
        }

        setCargaHoraria((int) ((quantidadeSemanasNaGraduacao * ValoresPadraoGraduacao.CARGA_HORARIA_SEMANAL_HORAS)
                - (this.faltas.size() * 1)));
        return this.cargaHoraria;
    }

    private long definirQuantidadeSemanasNaGraduacao(LocalDate inicioGraduacao, LocalDate fimGraduacao) {
        return ChronoUnit.WEEKS.between(inicioGraduacao, fimGraduacao);
    }

    public int getKyu() {
        return kyu;
    }

    public List<Falta> getFaltas() {
        return faltas;
    }

    public boolean isStatus() {
        return status;
    }

    public int getFrequencia() {
        return frequencia;
    }

    public LocalDate getInicioGraduacao() {
        return inicioGraduacao;
    }

    public LocalDate getFimGraduacao() {
        return LocalDate.now();
    }

    public int getNota() {
        return nota;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public int getDan() {
        return dan;
    }

    public static Graduacao gerarNovaGraduacaoCasoAprovado(int kyu, int dan) {

        Graduacao novaGraduacao = new Graduacao(kyu, dan);

        if (kyu == 1) {
            novaGraduacao.setKyu(0);
            novaGraduacao.setDan(1);
            return novaGraduacao;
        }

        if (kyu == 0) {
            novaGraduacao.setDan(dan + 1);
            return novaGraduacao;
        }

        novaGraduacao.setKyu(kyu - 1);
        return novaGraduacao;
    }
    
    public static Graduacao gerarNovaGraduacaoCasoReprovado(int kyu, int dan) {
       return new Graduacao(kyu, dan);
    }
}
