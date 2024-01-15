package br.org.institutobushido.dtos.aluno.objects.dados_sociais;

import com.mongodb.lang.NonNull;

import br.org.institutobushido.enums.Imovel;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record DadosSociaisDTORequest(
        @NonNull()
        boolean bolsaFamilia,
        @NonNull()
        boolean auxilioBrasil,
        @NonNull()
        Imovel imovel,
        @NonNull()
        int numerosDePessoasNaCasa,
        @NonNull()
        int contribuintesDaRendaFamiliar,
        @NonNull()
        boolean alunoContribuiParaRenda,
        @NonNull()
        int rendaFamiliarEmSalariosMinimos) {
}
