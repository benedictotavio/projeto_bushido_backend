package br.org.institutobushido.models.aluno.dados_escolares;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DadosEscolares implements Serializable {

    private static final long serialVersionUID = 2405172041950251807L;
    private String escola;

    public void setEscola(String escola) {
        if (escola == null || escola.isEmpty()) {
            return;
        }
        this.escola = escola;
    }
}
