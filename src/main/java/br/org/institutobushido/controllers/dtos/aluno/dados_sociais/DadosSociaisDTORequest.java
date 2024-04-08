package br.org.institutobushido.controllers.dtos.aluno.dados_sociais;

import br.org.institutobushido.enums.aluno.Imovel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record DadosSociaisDTORequest(

        boolean bolsaFamilia,

        boolean auxilioBrasil,

        @NotNull(message = "Imovel é obrigatório") @Pattern(regexp = "^(ALUGADO|PROPRIO|CEDIDO)$") Imovel imovel,

        int numerosDePessoasNaCasa,

        int contribuintesDaRendaFamiliar,

        boolean alunoContribuiParaRenda,

        @NotNull(message = "Informe a renda familiar em salarios minimos.") int rendaFamiliar) {
}
