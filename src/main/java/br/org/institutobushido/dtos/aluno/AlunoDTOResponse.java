package br.org.institutobushido.dtos.aluno;

import java.util.Date;

import br.org.institutobushido.enums.Imovel;
import br.org.institutobushido.enums.TipoDeTransporte;
import br.org.institutobushido.enums.Turno;

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
                String cpfResponsavel,
                int faltas,
                boolean status) {
}
