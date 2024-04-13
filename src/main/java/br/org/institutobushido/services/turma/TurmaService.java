package br.org.institutobushido.services.turma;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import br.org.institutobushido.controllers.dtos.turma.TurmaAlunoResponse;
import br.org.institutobushido.controllers.dtos.turma.TurmaDTORequest;
import br.org.institutobushido.controllers.dtos.turma.TurmaDTOResponse;
import br.org.institutobushido.mappers.turma.TurmaMapper;
import br.org.institutobushido.models.turma.Turma;
import br.org.institutobushido.repositories.TurmaRepositorio;
import br.org.institutobushido.resources.exceptions.AlreadyRegisteredException;
import br.org.institutobushido.resources.exceptions.EntityNotFoundException;

@Service
public class TurmaService implements TurmaServiceInterface {

    private final TurmaRepositorio turmaRepositorio;
    private MongoTemplate mongoTemplate;

    public TurmaService(TurmaRepositorio turmaRepositorio, MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        this.turmaRepositorio = turmaRepositorio;
    }

    @Override
    public String criarNovaTurma(TurmaDTORequest turma) {
        boolean turmaExiste = this.verificaSeTurmaExiste(turma.nome());

        if (turmaExiste) {
            throw new AlreadyRegisteredException("Turma com esse nome ja está registrada");
        }

        Turma novaTurma = TurmaMapper.mapToTurma(turma);
        this.turmaRepositorio.save(novaTurma);
        return "Turma criada " + turma.nome() + " com sucesso!";
    }

    @Override
    public String deletarTurma(String nomeTurma) {
        boolean turmaExiste = this.verificaSeTurmaExiste(nomeTurma);
        if (!turmaExiste) {
            throw new EntityNotFoundException("Turma com esse nome não existe");
        }
        this.turmaRepositorio.deleteByNome(nomeTurma);
        return "Turma " + nomeTurma + " deletada com sucesso";
    }

    @Override
    public List<TurmaDTOResponse> listarTurmas() {
        List<Turma> turmas = this.turmaRepositorio.findAll();
        return TurmaMapper.mapToListTurmaDTOResponse(turmas);
    }

    @Override
    public TurmaDTOResponse buscarTurmaPorNome(String nomeTurma) {
        return TurmaMapper.mapToTurmaDTOResponse(this.encontrarTurmaPeloNome(nomeTurma));
    }

    @Override
    public List<TurmaAlunoResponse> listarAlunosDaTurma(String nomeTurma) {
        AggregationOperation match = Aggregation.match(Criteria.where("nome").is(nomeTurma));

        AggregationOperation lookup = Aggregation.lookup("alunos", "nome", "turma", "alunos_turma");

        AggregationOperation unwind = Aggregation.unwind("$alunos_turma");

        AggregationOperation project = Aggregation.project()
                .and("alunos_turma.nome").as("nome")
                .and("alunos_turma._id").as("rg")
                .and("alunos_turma.genero").as("genero")
                .and("alunos_turma.dataNascimento").as("dataNascimento")
                .andExclude("_id");

        Aggregation aggregation = Aggregation.newAggregation(match, lookup, unwind, project);
        var result = mongoTemplate.aggregate(aggregation, "turmas", TurmaAlunoResponse.class);
        return result.getMappedResults();
    }

    private boolean verificaSeTurmaExiste(String nomeTurma) {
        return this.turmaRepositorio.findByNome(nomeTurma).isPresent();
    }

    private Turma encontrarTurmaPeloNome(String nomeTurma) {
        return this.turmaRepositorio.findByNome(nomeTurma).orElseThrow(
                () -> new EntityNotFoundException("Turma com esse nome não existe"));
    }
}
