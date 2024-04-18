package br.org.institutobushido.services.turma;

import java.util.List;

import br.org.institutobushido.controllers.dtos.turma.TurmaAlunoDTOResponse;
import br.org.institutobushido.controllers.dtos.turma.TurmaDTORequest;
import br.org.institutobushido.controllers.dtos.turma.TurmaDTOResponse;

public interface TurmaServiceInterface {
    String criarNovaTurma(String emailAdmin, TurmaDTORequest turma);

    String deletarTurma(String emailAdmin, String nomeTurma);

    List<TurmaDTOResponse> listarTurmas();

    TurmaDTOResponse buscarTurmaPorNome(String nomeTurma);

    public List<TurmaAlunoDTOResponse> listarAlunosDaTurma(String nomeTurma);
}
