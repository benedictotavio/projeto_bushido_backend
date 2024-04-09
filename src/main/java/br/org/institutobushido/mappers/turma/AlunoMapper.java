package br.org.institutobushido.mappers.turma;

import java.util.List;
import java.util.stream.Collectors;

import br.org.institutobushido.controllers.dtos.turma.aluno.AlunoDTORequest;
import br.org.institutobushido.controllers.dtos.turma.aluno.AlunoDTOResponse;
import br.org.institutobushido.models.turma.Aluno;

public class AlunoMapper {

    private AlunoMapper() {
    }

    public static Aluno mapToAluno(AlunoDTORequest alunoDTORequest) {
        if (alunoDTORequest == null) {
            return null;
        }

        return new Aluno(
                alunoDTORequest.rg(),
                alunoDTORequest.nome(),
                alunoDTORequest.dataNascimento(),
                alunoDTORequest.genero());
    }

    public static AlunoDTOResponse mapToAlunoDTOResponse(Aluno aluno) {

        if (aluno == null) {
            return null;
        }

        return new AlunoDTOResponse(
                 aluno.getRg(),
                aluno.getNome(),
                aluno.getDataNascimento().toString(),
                aluno.getGenero()
                );
    }

    public static List<AlunoDTOResponse> mapToListAlunoDTOResponse(List<Aluno> alunos) {
        return alunos.stream().map(AlunoMapper::mapToAlunoDTOResponse).collect(Collectors.toList());
    }
}