package br.org.institutobushido.services.turma;

import java.util.List;

import br.org.institutobushido.controllers.dtos.turma.TurmaDTORequest;
import br.org.institutobushido.controllers.dtos.turma.TurmaDTOResponse;
import br.org.institutobushido.controllers.dtos.turma.aluno.AlunoDTORequest;

public interface TurmaServiceInterface {
    void criarNovaTurma(TurmaDTORequest turma);

    void deletarTurma(String nomeTurma);

    void adicionarAlunoATurma(String nomeTurma, AlunoDTORequest aluno);

    List<TurmaDTOResponse> listarTurmas();

    TurmaDTOResponse buscarTurmaPorNome(String nomeTurma);
}
