package br.org.institutobushido.controllers.dtos.aluno.dados_sociais;

import br.org.institutobushido.enums.aluno.Imovel;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record UpdateDadosSociaisDTORequest(
    boolean bolsaFamilia,
    boolean auxilioBrasil,
    Imovel imovel,
    int numerosDePessoasNaCasa,
    int contribuintesDaRendaFamiliar,
    boolean alunoContribuiParaRenda,
    int rendaFamiliar
) {
}
