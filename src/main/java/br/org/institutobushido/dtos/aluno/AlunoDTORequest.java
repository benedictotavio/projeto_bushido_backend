package br.org.institutobushido.dtos.aluno;

import java.util.Date;

import com.mongodb.lang.NonNull;

import br.org.institutobushido.enums.Imovel;
import br.org.institutobushido.enums.TipoDeTransporte;
import br.org.institutobushido.enums.Turno;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AlunoDTORequest(
        @NotNull(message = "Nome é obrigatório") String nome,

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

        @NonNull() @Pattern(regexp = "^\\d{9}$", message = "Formato de rg inválido!") String rg,
        String cpfResponsavel,

        int faltas,

        boolean status) {
}