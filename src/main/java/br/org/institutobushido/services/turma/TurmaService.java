package br.org.institutobushido.services.turma;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.org.institutobushido.controllers.dtos.turma.TurmaDTORequest;
import br.org.institutobushido.mappers.turma.TurmaMapper;
import br.org.institutobushido.model.turma.Turma;
import br.org.institutobushido.repositories.TurmaRepositorio;

@Service
public class TurmaService implements TurmaServiceInterface {

    private final TurmaRepositorio turmaRepositorio;

    public TurmaService(TurmaRepositorio turmaRepositorio) {
        this.turmaRepositorio = turmaRepositorio;
    }

    @Override
    public void criarNovaTurma(TurmaDTORequest turma) {
        Turma novaTurma = TurmaMapper.mapToTurma(turma);
        this.turmaRepositorio.save(novaTurma);
    }

    // TODO: Implementar metodo com regra de negocio, para adição de faltas em todos alunos selecionados
    @Override
    public int adicionarFaltaOsAluno(List<String> rg) {
        return 0;
    }

    @Override
    public void deletarTurma(UUID turmaId) {
        this.turmaRepositorio.deleteById(turmaId);
     }
    
}
