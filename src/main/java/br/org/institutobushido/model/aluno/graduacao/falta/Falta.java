package br.org.institutobushido.model.aluno.graduacao.falta;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;

@Data
public class Falta implements Serializable {

    private static final long serialVersionUID = 2405172041950251807L;

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