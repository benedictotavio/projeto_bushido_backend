package br.org.institutobushido.services.turma;

import java.util.List;

import br.org.institutobushido.controllers.dtos.turma.TurmaDTORequest;
import br.org.institutobushido.controllers.dtos.turma.TurmaDTOResponse;
import br.org.institutobushido.controllers.dtos.turma.aluno.AlunoDTORequest;

public interface TurmaServiceInterface {
    String criarNovaTurma(TurmaDTORequest turma);

    String deletarTurma(String nomeTurma);

    String adicionarAlunoATurma(String nomeTurma, AlunoDTORequest aluno);

    String removerAlunoDaTurma(String nomeTurma, String rg);

    List<TurmaDTOResponse> listarTurmas();

    TurmaDTOResponse buscarTurmaPorNome(String nomeTurma);
}
