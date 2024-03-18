package br.org.institutobushido.model.aluno.dados_escolares;

import br.org.institutobushido.enums.aluno.Turno;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DadosEscolares {
    private Turno turno;
    private String escola;
    private String serie;

    public void setTurno(Turno turno) {
        if (turno == null) {
            turno = this.turno;
        }
        this.turno = turno;
    }

    public void setEscola(String escola) {
        if (escola == null) {
            escola = this.escola;
        }
        this.escola = escola;
    }

    public void setSerie(String serie) {
        if (serie == null) {
            serie = this.serie;
        }
        this.serie = serie;
    }
}
