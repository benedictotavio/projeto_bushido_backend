package br.org.institutobushido.model.aluno.objects;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Faltas {

    public Faltas(String motivo, String observacao) {
        this.motivo = motivo;
        this.observacao = observacao;
    }

    @Id
    private int faltasId = new Date().hashCode();
    private String data = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    private String motivo;
    private String observacao;

}