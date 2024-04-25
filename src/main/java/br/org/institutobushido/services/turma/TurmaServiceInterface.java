package br.org.institutobushido.services.turma;

import java.util.List;

import br.org.institutobushido.controllers.dtos.turma.DadosTurmaDTOResponse;
import br.org.institutobushido.controllers.dtos.turma.TurmaDTORequest;
import br.org.institutobushido.controllers.dtos.turma.TurmaDTOResponse;

public interface TurmaServiceInterface {
    String criarNovaTurma(TurmaDTORequest turma);

    String deletarTurma(String emailAdmin, String nomeTurma);

    List<TurmaDTOResponse> listarTurmas(long dataInicial, long dataFinal);

    TurmaDTOResponse buscarTurmaPorNome(String nomeTurma);

    DadosTurmaDTOResponse listarAlunosDaTurma(String nomeTurma);
}
