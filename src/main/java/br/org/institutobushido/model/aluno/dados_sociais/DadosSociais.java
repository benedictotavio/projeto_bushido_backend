package br.org.institutobushido.model.aluno.dados_sociais;

import br.org.institutobushido.enums.aluno.Imovel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DadosSociais {
    private boolean bolsaFamilia;
    private boolean auxilioBrasil;
    private Imovel imovel;
    private int numerosDePessoasNaCasa;
    private int contribuintesDaRendaFamiliar;
    private boolean alunoContribuiParaRenda;
    private int rendaFamiliarEmSalariosMinimos;
}
