package br.org.institutobushido.dtos.aluno.dados_sociais;

import br.org.institutobushido.enums.aluno.Imovel;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record DadosSociaisDTOResponse(
        boolean bolsaFamilia,
        boolean auxilioBrasil,
        Imovel imovel,
        int numerosDePessoasNaCasa,
        int contribuintesDaRendaFamiliar,
        boolean alunoContribuiParaRenda,
        int rendaFamiliarEmSalariosMinimos) {
}
