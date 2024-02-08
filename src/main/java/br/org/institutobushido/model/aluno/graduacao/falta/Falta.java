package br.org.institutobushido.model.aluno.graduacao.falta;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;

@Data
public class Falta {

    public Falta(String motivo, String observacao) {
        this.data = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        this.motivo = motivo;
        this.observacao = observacao;
    }

    public Falta() {
    }

    private String data;
    private String motivo;
    private String observacao;

}