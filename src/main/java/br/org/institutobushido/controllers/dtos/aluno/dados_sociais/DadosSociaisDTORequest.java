package br.org.institutobushido.controllers.dtos.aluno.dados_sociais;

import br.org.institutobushido.enums.aluno.Imovel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosSociaisDTORequest(

        boolean bolsaFamilia,

        boolean auxilioBrasil,

        @NotNull(message = "Imovel é obrigatório")
        @Pattern(regexp = "^(ALUGADO|PROPRIO|CEDIDO)$")
        Imovel imovel,

        @NotNull(message = "Numeros de pessoas na casa é obrigatório")
        @Min(1)
        int numerosDePessoasNaCasa,

        @NotNull(message = "Contribuintes da renda familiar é obrigatório")
        @Min(1)
        int contribuintesDaRendaFamiliar,

        boolean alunoContribuiParaRenda,

        @NotNull(message = "Informe a renda familiar em salarios minimos.")
        @Min(1)
        int rendaFamiliar) {
}
