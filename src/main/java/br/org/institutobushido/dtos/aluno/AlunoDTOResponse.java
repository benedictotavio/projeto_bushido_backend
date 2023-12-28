package br.org.institutobushido.dtos.aluno;

import java.util.Date;
import java.util.List;

import br.org.institutobushido.enums.Imovel;
import br.org.institutobushido.enums.TipoDeTransporte;
import br.org.institutobushido.enums.Turno;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record AlunoDTOResponse(
        String nome,
        boolean bolsaFamilia,
        boolean auxilioBrasil,
        Imovel imovel,
        int numerosDePessoasNaCasa,
        int contribuintesDaRendaFamiliar,
        boolean alunoContribuiParaRenda,
        int rendaFamiliarEmSalariosMinimos,
        TipoDeTransporte transporte,
        boolean vemAcompanhado,
        Turno turno,
        Date dataPreenchimento,
        String cidade,
        String estado,
        String rg,
        List<ResponsavelDTOResponse> responsaveis,
        int faltas,
        boolean status) {
}
