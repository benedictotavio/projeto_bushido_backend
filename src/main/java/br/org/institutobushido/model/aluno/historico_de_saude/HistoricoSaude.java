package br.org.institutobushido.model.aluno.historico_de_saude;

import java.util.ArrayList;
import java.util.List;

import br.org.institutobushido.enums.aluno.FatorRH;
import br.org.institutobushido.enums.aluno.TipoSanguineo;
import br.org.institutobushido.model.aluno.historico_de_saude.informacoes_saude.Alergia;
import br.org.institutobushido.model.aluno.historico_de_saude.informacoes_saude.Cirurgia;
import br.org.institutobushido.model.aluno.historico_de_saude.informacoes_saude.DoencaCronica;
import br.org.institutobushido.model.aluno.historico_de_saude.informacoes_saude.UsoMedicamentoContinuo;
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
    private Alergia alergia;
    private Cirurgia cirurgia;
    private List<String> deficiencias = new ArrayList<>();
    private List<String> acompanhamentoSaude = new ArrayList<>();
}