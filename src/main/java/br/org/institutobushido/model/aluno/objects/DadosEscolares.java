package br.org.institutobushido.model.aluno.objects;

import br.org.institutobushido.enums.Turno;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DadosEscolares {
    private Turno turno;
    private String escola;
    private String serie;
}
