package br.org.institutobushido.services.turma;

import java.util.List;

import br.org.institutobushido.controllers.dtos.turma.TurmaAlunoResponse;
import br.org.institutobushido.controllers.dtos.turma.TurmaDTORequest;
import br.org.institutobushido.controllers.dtos.turma.TurmaDTOResponse;

public interface TurmaServiceInterface {
    String criarNovaTurma(TurmaDTORequest turma);

    String deletarTurma(String nomeTurma);

    List<TurmaDTOResponse> listarTurmas();

    TurmaDTOResponse buscarTurmaPorNome(String nomeTurma);

    public List<TurmaAlunoResponse> listarAlunosDaTurma(String nomeTurma);
}
