package br.org.institutobushido.services.turma;

import java.util.Date;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import br.org.institutobushido.controllers.dtos.turma.DadosTurmaDTOResponse;
import br.org.institutobushido.controllers.dtos.turma.TurmaAlunoDTOResponse;
import br.org.institutobushido.controllers.dtos.turma.TurmaDTORequest;
import br.org.institutobushido.controllers.dtos.turma.TurmaDTOResponse;
import br.org.institutobushido.mappers.turma.TurmaMapper;
import br.org.institutobushido.models.admin.Admin;
import br.org.institutobushido.models.admin.turmas.TurmaResponsavel;
import br.org.institutobushido.models.aluno.Aluno;
import br.org.institutobushido.models.turma.Turma;
import br.org.institutobushido.models.turma.tutor.Tutor;
import br.org.institutobushido.repositories.AdminRepositorio;
import br.org.institutobushido.repositories.TurmaRepositorio;
import br.org.institutobushido.resources.exceptions.AlreadyRegisteredException;
import br.org.institutobushido.resources.exceptions.EntityNotFoundException;
import br.org.institutobushido.resources.exceptions.LimitQuantityException;

@Service
public class TurmaService implements TurmaServiceInterface {

    private final TurmaRepositorio turmaRepositorio;
    private final MongoTemplate mongoTemplate;
    private final AdminRepositorio adminRepositorio;

    public TurmaService(TurmaRepositorio turmaRepositorio, MongoTemplate mongoTemplate,
            AdminRepositorio adminRepositorio) {
        this.mongoTemplate = mongoTemplate;
        this.turmaRepositorio = turmaRepositorio;
        this.adminRepositorio = adminRepositorio;
    }

    @CacheEvict(value = { "turma", "admin" }, allEntries = true)
    @Override
    public String criarNovaTurma(TurmaDTORequest turma) {

        if (this.verificaSeTurmaExiste(turma.nome())) {
            throw new AlreadyRegisteredException("Turma " + turma.nome() + " ja está registrada.");
        }

        Turma novaTurma = TurmaMapper.mapToTurma(turma);

        Admin admin = this.vinculrTurmaAoAdmin(turma.tutor().email(),
                new TurmaResponsavel(novaTurma.getEndereco(), novaTurma.getNome()));

        novaTurma.setTutor(
                new Tutor(admin.getNome(), admin.getEmail()));

        this.turmaRepositorio.save(novaTurma);
        return "Turma " + turma.nome() + " foi criada com sucesso!";
    }

    @CacheEvict(value = { "turma", "admin" }, allEntries = true)
    @Override
    public String deletarTurma(String emailAdmin, String nomeTurma) {

        if (!this.verificaSeTurmaExiste(nomeTurma)) {
            throw new EntityNotFoundException("Turma com esse nome não existe");
        }

        if (this.vefrificarSeExistemAlunosAtivosNaTurma(nomeTurma)) {
            throw new LimitQuantityException("Turma com alunos não pode ser deletada");
        }

        this.desvicularTurmaDoAdmin(emailAdmin, nomeTurma);

        this.turmaRepositorio.deleteByNome(nomeTurma);
        return "Turma " + nomeTurma + " deletada com sucesso";
    }

    @Override
    public List<TurmaDTOResponse> listarTurmas(long dataInicial, long dataFinal) {

        if (dataInicial > System.currentTimeMillis()) {
            throw new LimitQuantityException("Data inicial deve ser menor que data atual");
        }

        if (dataInicial == 0 && dataFinal == 0) {
            return TurmaMapper.mapToListTurmaDTOResponse(this.turmaRepositorio.findAll());
        }

        if (dataFinal == 0) {
            dataFinal = System.currentTimeMillis();
        }

        if (dataInicial >= dataFinal) {
            throw new LimitQuantityException("Data inicial deve ser menor que data final");
        }

        Criteria criteria = Criteria.where("dataCriacao")
                .gte(new Date(dataInicial))
                .lte(new Date(dataFinal));
        Query query = new Query();
        query.addCriteria(criteria).with(Sort.by(Sort.Direction.ASC, "dataCriacao"));
        return TurmaMapper.mapToListTurmaDTOResponse(this.mongoTemplate.find(query, Turma.class));
    }

    @Override
    public TurmaDTOResponse buscarTurmaPorNome(String nomeTurma) {
        return TurmaMapper.mapToTurmaDTOResponse(this.encontrarTurmaPeloNome(nomeTurma));
    }

    @Override
    public DadosTurmaDTOResponse listarAlunosDaTurma(String nomeTurma) {
        Turma turma = this.encontrarTurmaPeloNome(nomeTurma);
        return new DadosTurmaDTOResponse(turma.getTutor().getEmail(), this.listarAlunosAtivosDaTurma(turma.getNome()));
    }

    private List<TurmaAlunoDTOResponse> listarAlunosAtivosDaTurma(String nomeTurma) {
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
        return mongoTemplate.aggregate(aggregation, "turmas", TurmaAlunoDTOResponse.class).getMappedResults();
    }

    private boolean verificaSeTurmaExiste(String nomeTurma) {
        Query query = new Query(Criteria.where("nome").regex(nomeTurma, "si"));
        return this.mongoTemplate.exists(query, Turma.class);
    }

    private Admin vinculrTurmaAoAdmin(String emailAdmin, TurmaResponsavel turma) {
        Admin admin = this.encontrarAdminPeloEmail(emailAdmin);
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(emailAdmin));
        Update update = new Update();
        update.push("turmas", admin.adicionarTurma(turma));
        this.mongoTemplate.updateFirst(query, update, Admin.class);
        return admin;
    }

    private void desvicularTurmaDoAdmin(String emailAdmin, String nomeTurma) {
        Admin admin = this.encontrarAdminPeloEmail(emailAdmin);
        String turma = admin.removerTurma(nomeTurma);
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(emailAdmin));
        Update update = new Update();
        update.pull("turmas", Query.query(Criteria.where("nome").is(turma)));
        this.mongoTemplate.updateFirst(query, update, Admin.class);
    }

    private boolean vefrificarSeExistemAlunosAtivosNaTurma(String nomeTurma) {
        Query query = new Query();
        query.addCriteria(
                Criteria.where("turma").is(nomeTurma).and("graduacao").elemMatch(Criteria.where("status").is(true)));
        return this.mongoTemplate.exists(query, Aluno.class);
    }

    @Cacheable(value = "turma", key = "#nomeTurma")
    private Turma encontrarTurmaPeloNome(String nomeTurma) {
        Query query = new Query(Criteria.where("nome").regex(nomeTurma, "si"));
        Turma turma = this.mongoTemplate.findOne(query, Turma.class);

        if (turma == null) {
            throw new EntityNotFoundException("Turma com esse nome não existe");
        }

        return turma;
    }

    @Cacheable(value = "admin", key = "#email")
    private Admin encontrarAdminPeloEmail(String email) {
        return this.adminRepositorio.findByEmailAdmin(email).orElseThrow(
                () -> new EntityNotFoundException("Admin com esse email não existe"));
    }
}
