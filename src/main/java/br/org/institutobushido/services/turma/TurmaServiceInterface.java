package br.org.institutobushido.services.turma;

import java.util.List;
import java.util.UUID;

import br.org.institutobushido.controllers.dtos.turma.TurmaDTORequest;

public interface TurmaServiceInterface {
    void criarNovaTurma(TurmaDTORequest turma);
    void deletarTurma(UUID turmaId);
    int adicionarFaltaOsAluno(List<String> rg);
}
