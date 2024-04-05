package br.org.institutobushido.controllers.dtos.turma.aluno;

import br.org.institutobushido.enums.aluno.Genero;

public record AlunoDTOResponse(
        String nome,
        String dataNascimento,
        Genero genero,
        String rg,
        String dataPreenchimento) {
}
