package br.org.institutobushido.model.aluno.graduacao.falta;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Falta implements Serializable {

    private static final long serialVersionUID = 2405172041950251807L;

    public Falta(String motivo, String observacao, Date data) {
        this.data = new SimpleDateFormat("dd-MM-yyyy").format(data);
        this.motivo = motivo;
        this.observacao = observacao;
    }

    private String data;
    private String motivo;
    private String observacao;

}