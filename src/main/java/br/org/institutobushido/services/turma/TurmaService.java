package br.org.institutobushido.services.turma;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import br.org.institutobushido.controllers.dtos.turma.TurmaDTORequest;
import br.org.institutobushido.controllers.dtos.turma.TurmaDTOResponse;
import br.org.institutobushido.controllers.dtos.turma.aluno.AlunoDTORequest;
import br.org.institutobushido.mappers.turma.AlunoMapper;
import br.org.institutobushido.mappers.turma.TurmaMapper;
import br.org.institutobushido.models.turma.Aluno;
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
    public void criarNovaTurma(TurmaDTORequest turma) {
        boolean turmaExiste = this.verificaSeTurmaExiste(turma.nome());

        if (turmaExiste) {
            throw new AlreadyRegisteredException("Turma com esse nome ja está registrada");
        }

        Turma novaTurma = TurmaMapper.mapToTurma(turma);
        this.turmaRepositorio.save(novaTurma);

    }

    @Override
    public void adicionarAlunoATurma(String nomeTurma, AlunoDTORequest aluno) {
        Turma turma = this.encontrarTurmaPeloNome(nomeTurma);
        Aluno alunoAdicionado = AlunoMapper.mapToAluno(aluno);
        turma.adicionarAluno(alunoAdicionado);
        Query query = new Query();
        query.addCriteria(Criteria.where("nome").is(nomeTurma));
        Update update = new Update().push("alunos", alunoAdicionado);
        mongoTemplate.updateFirst(query, update, Turma.class);
    }

    @Override
    public void removerAlunoDaTurma(String nomeTurma, String rg) {
        Turma turma = this.encontrarTurmaPeloNome(nomeTurma);
        turma.removerAluno(rg);
        Query query = new Query();
        query.addCriteria(Criteria.where("nome").is(nomeTurma));
        Update update = new Update().pull("alunos", new Query().addCriteria(Criteria.where("rg").is(rg)));
        mongoTemplate.updateFirst(query, update, Turma.class);
    }

    @Override
    public void deletarTurma(String nomeTurma) {
        boolean turmaExiste = this.verificaSeTurmaExiste(nomeTurma);
        if (!turmaExiste) {
            throw new EntityNotFoundException("Turma com esse nome não existe");
        }
        this.turmaRepositorio.deleteByNome(nomeTurma);
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

    private boolean verificaSeTurmaExiste(String nomeTurma) {
        return this.turmaRepositorio.findByNome(nomeTurma).isPresent();
    }

    private Turma encontrarTurmaPeloNome(String nomeTurma) {
        return this.turmaRepositorio.findByNome(nomeTurma).orElseThrow(
                () -> new EntityNotFoundException("Turma com esse nome não existe"));
    }

}
