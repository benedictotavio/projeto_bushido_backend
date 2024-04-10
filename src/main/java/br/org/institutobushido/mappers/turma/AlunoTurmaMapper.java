package br.org.institutobushido.mappers.turma;

import java.util.List;
import java.util.stream.Collectors;

import br.org.institutobushido.controllers.dtos.turma.aluno.AlunoTurmaDTORequest;
import br.org.institutobushido.controllers.dtos.turma.aluno.AlunoTurmaDTOResponse;
import br.org.institutobushido.models.turma.Aluno;

public class AlunoTurmaMapper {

    private AlunoTurmaMapper() {
    }

    public static Aluno mapToAluno(AlunoTurmaDTORequest alunoDTORequest) {
        if (alunoDTORequest == null) {
            return null;
        }

        return new Aluno(
                alunoDTORequest.rg(),
                alunoDTORequest.nome(),
                alunoDTORequest.dataNascimento(),
                alunoDTORequest.genero());
    }

    public static AlunoTurmaDTOResponse mapToAlunoDTOResponse(Aluno aluno) {

        if (aluno == null) {
            return null;
        }

        return new AlunoTurmaDTOResponse(
                 aluno.getRg(),
                aluno.getNome(),
                aluno.getDataNascimento().toString(),
                aluno.getGenero()
                );
    }

    public static List<AlunoTurmaDTOResponse> mapToListAlunoDTOResponse(List<Aluno> alunos) {
        return alunos.stream().map(AlunoTurmaMapper::mapToAlunoDTOResponse).collect(Collectors.toList());
    }
}