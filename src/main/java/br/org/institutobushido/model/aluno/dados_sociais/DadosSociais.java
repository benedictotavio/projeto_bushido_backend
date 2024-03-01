package br.org.institutobushido.model.aluno.dados_sociais;

import br.org.institutobushido.enums.aluno.Imovel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DadosSociais {
    private boolean bolsaFamilia = false;
    private boolean auxilioBrasil = false;
    private Imovel imovel;
    private int numerosDePessoasNaCasa = 1;
    private int contribuintesDaRendaFamiliar = 1;
    private boolean alunoContribuiParaRenda = false;
    private int rendaFamiliarEmSalariosMinimos;
}
