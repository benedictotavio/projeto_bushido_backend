package br.org.institutobushido.model.aluno.dados_escolares;

import java.io.Serializable;

import br.org.institutobushido.enums.aluno.Turno;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DadosEscolares implements Serializable {

    private static final long serialVersionUID = 2405172041950251807L;

    private Turno turno;
    private String escola;
    private String serie;

    public void setTurno(Turno turno) {
        if (turno == null) {
            return;
        }
        this.turno = turno;
    }

    public void setEscola(String escola) {
        if (escola == null) {
            return;
        }
        this.escola = escola;
    }

    public void setSerie(String serie) {
        if (serie == null) {
            return;
        }
        this.serie = serie;
    }
}
