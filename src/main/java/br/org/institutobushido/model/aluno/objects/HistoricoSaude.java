package br.org.institutobushido.model.aluno.objects;

import java.util.List;

import br.org.institutobushido.enums.FatorRH;
import br.org.institutobushido.enums.TipoSanguineo;
import br.org.institutobushido.model.aluno.historico_de_saude.Alergias;
import br.org.institutobushido.model.aluno.historico_de_saude.Cirurgias;
import br.org.institutobushido.model.aluno.historico_de_saude.DoencaCronica;
import br.org.institutobushido.model.aluno.historico_de_saude.UsoMedicamentoContinuo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoSaude {
    private TipoSanguineo tipoSanguineo;
    private FatorRH fatorRh;
    private UsoMedicamentoContinuo usoMedicamentoContinuo;
    private DoencaCronica doencaCronica;
    private Alergias alergias;
    private Cirurgias cirurgias;
    private List<String> deficiencias;
    private List<String> acompanhamentoSaude;
}