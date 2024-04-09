package br.org.institutobushido.controllers.dtos.turma.aluno;

import br.org.institutobushido.enums.aluno.Genero;

public record AlunoTurmaDTOResponse(
                String rg,
                String nome,
                String dataNascimento,
                Genero genero
                ) {
}
