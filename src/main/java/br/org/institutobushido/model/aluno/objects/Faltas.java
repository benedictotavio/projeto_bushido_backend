package br.org.institutobushido.model.aluno.objects;

import java.util.Date;

import org.springframework.data.annotation.Id;
import lombok.Data;

@Data
public class Faltas {

    public Faltas(String motivo, Date data, String observacao) {
        this.motivo = motivo;
        this.data = data;
        this.observacao = observacao;
    }

    @Id
    private int faltasId = new Date().hashCode();
    private String motivo;
    private Date data;
    private String observacao;
}