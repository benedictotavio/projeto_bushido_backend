package br.org.institutobushido.services.aluno;

import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import br.org.institutobushido.controllers.dtos.aluno.graduacao.GraduacaoDTOResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import br.org.institutobushido.controllers.dtos.aluno.AlunoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.AlunoDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.UpdateAlunoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.faltas.FaltaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.UpdateHistoricoSaudeDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.responsavel.ResponsavelDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.responsavel.ResponsavelDTOResponse;
import br.org.institutobushido.mappers.aluno.AlunoMapper;
import br.org.institutobushido.mappers.aluno.DadosEscolaresMapper;
import br.org.institutobushido.mappers.aluno.DadosSociaisMapper;
import br.org.institutobushido.mappers.aluno.EnderecoMapper;
import br.org.institutobushido.mappers.aluno.GraduacaoMapper;
import br.org.institutobushido.model.aluno.Aluno;
import br.org.institutobushido.model.aluno.dados_escolares.DadosEscolares;
import br.org.institutobushido.model.aluno.dados_sociais.DadosSociais;
import br.org.institutobushido.model.aluno.endereco.Endereco;
import br.org.institutobushido.model.aluno.graduacao.Graduacao;
import br.org.institutobushido.model.aluno.graduacao.falta.Falta;
import br.org.institutobushido.model.aluno.historico_de_saude.informacoes_saude.Alergia;
import br.org.institutobushido.model.aluno.historico_de_saude.informacoes_saude.Cirurgia;
import br.org.institutobushido.model.aluno.historico_de_saude.informacoes_saude.DoencaCronica;
import br.org.institutobushido.model.aluno.historico_de_saude.informacoes_saude.UsoMedicamentoContinuo;
import br.org.institutobushido.model.aluno.responsaveis.Responsavel;
import br.org.institutobushido.repositories.AlunoRepositorio;
import br.org.institutobushido.resources.exceptions.AlreadyRegisteredException;
import br.org.institutobushido.resources.exceptions.EntityNotFoundException;
import br.org.institutobushido.resources.exceptions.InactiveUserException;
import br.org.institutobushido.resources.exceptions.LimitQuantityException;

@Service
public class AlunoServices implements AlunoServicesInterface {

    private AlunoRepositorio alunoRepositorio;
    private MongoTemplate mongoTemplate;

    public AlunoServices(AlunoRepositorio alunoRepositorio, MongoTemplate mongoTemplate) {
        this.alunoRepositorio = alunoRepositorio;
        this.mongoTemplate = mongoTemplate;
    }

    private static final String GRADUACAO = "graduacao";
    private static final String DATA_FORMATO = "dd-MM-yyyy";
    private static final String HISTORICO_SAUDE = "historicoSaude.";

    @Override
    public AlunoDTOResponse adicionarAluno(AlunoDTORequest alunoDTORequest) {

        Optional<Aluno> alunoEncontrado = alunoRepositorio.findByRg(alunoDTORequest.rg());

        if (alunoEncontrado.isPresent()) {
            throw new AlreadyRegisteredException("O Aluno com o rg " + alunoDTORequest.rg() + " ja esta cadastrado!");
        }

        Aluno novoAlunoRequest = AlunoMapper.mapToAluno(alunoDTORequest);

        Aluno novoAluno = alunoRepositorio.save(novoAlunoRequest);

        return AlunoMapper.mapToAlunoDTOResponse(novoAluno);
    }

    @Override
    public AlunoDTOResponse buscarAluno(String rg) {
        Aluno alunoEncontrado = encontrarAlunoPorRg(rg);

        return AlunoMapper.mapToAlunoDTOResponse(alunoEncontrado);
    }

    @Override
    public ResponsavelDTOResponse adicionarResponsavel(String rg, ResponsavelDTORequest responsavelDTORequest) {
        Aluno aluno = encontrarAlunoPorRg(rg);
        Optional<Responsavel> responsavel = encontrarResponsavelPorCpf(aluno, responsavelDTORequest.cpf());

        if (responsavel.isPresent()) {
            throw new AlreadyRegisteredException("Esse responsável já existe");
        }

        Query query = new Query();

        query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
        Update update = new Update().push("responsaveis", responsavelDTORequest);
        mongoTemplate.updateFirst(query, update, Aluno.class);
        return ResponsavelDTOResponse.builder().withCpf(responsavelDTORequest.cpf())
                .withEmail(responsavelDTORequest.email()).withFiliacao(responsavelDTORequest.filiacao().toString())
                .withNome(responsavelDTORequest.nome())
                .withTelefone(responsavelDTORequest.telefone()).build();

    }

    @Override
    public String removerResponsavel(String rg, String cpf) {
        Aluno aluno = encontrarAlunoPorRg(rg);
        Optional<Responsavel> responsavel = encontrarResponsavelPorCpf(aluno, cpf);

        if (aluno.getResponsaveis().size() == 1) {
            throw new LimitQuantityException("O aluno deve ter pelo menos 1 responsavel!");
        }

        if (!responsavel.isPresent()) {
            throw new EntityNotFoundException("Responsavel com o CPF: " + cpf + " nao foi encontrado.");
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
        Update update = new Update().pull("responsaveis", Query.query(Criteria.where("cpf").is(cpf)));
        mongoTemplate.updateFirst(query, update, Aluno.class);
        return String.valueOf(aluno.getResponsaveis().size() - 1);
    }

    @Override
    public String adicionarFaltaDoAluno(String rg, FaltaDTORequest falta, long dataFalta) {
        Aluno aluno = encontrarAlunoPorRg(rg);
        int graduacaoAtual = aluno.getGraduacao().size();

        if (!aluno.getGraduacao().get(graduacaoAtual - 1).isStatus()) {
            throw new InactiveUserException("O Aluno esta inativo. Pois o mesmo se encontra com mais de 5 faltas");
        }

        if (dataFalta > new Date().getTime()) {
            throw new LimitQuantityException("A data deve ser menor ou igual a data atual");
        }

        if (dataFalta < aluno.getGraduacao().get(graduacaoAtual - 1).getInicioGraduacao().atStartOfDay()
                .toInstant(ZoneOffset.UTC).toEpochMilli()) {
            throw new LimitQuantityException("A data deve ser maior ou igual a data de inicio da graduacao");
        }

        boolean faltasDoAluno = checarSeFaltaEstaRegistrada(aluno, new Date(dataFalta));

        if (faltasDoAluno) {
            throw new AlreadyRegisteredException("Ja existe um registro de falta nessa data");
        }

        Falta novaFalta = new Falta();

        novaFalta.setData(new SimpleDateFormat(DATA_FORMATO).format(new Date(dataFalta)));
        novaFalta.setMotivo(falta.motivo());
        novaFalta.setObservacao(falta.observacao());
        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
        Update update = new Update().addToSet(GRADUACAO + "." + (graduacaoAtual - 1) + ".faltas", novaFalta);
        mongoTemplate.updateFirst(query, update, Aluno.class);

        if (aluno.getGraduacao().get(0).getFaltas().size() == 4) {
            mudarStatusGraduacaoAluno(aluno, false);
        }

        return String.valueOf(aluno.getGraduacao().get(graduacaoAtual - 1).getFaltas().size() + 1);
    }

    @Override
    public String retirarFaltaDoAluno(String rg, String faltasId) {
        Aluno aluno = encontrarAlunoPorRg(rg);
        int graduacaoAtual = aluno.getGraduacao().size();
        Falta faltasDoAluno = encontrarFaltasDoAluno(aluno, faltasId);

        if (aluno.getGraduacao().get(aluno.getGraduacao().size() - 1).getFaltas().size() == 5) {
            mudarStatusGraduacaoAluno(aluno, true);
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
        Update update = new Update().pull(GRADUACAO + "." +(graduacaoAtual - 1) + ".faltas", faltasDoAluno);
        mongoTemplate.updateFirst(query, update, Aluno.class);
        return String.valueOf(aluno.getGraduacao().get(0).getFaltas().size() - 1);
    }

    @Override
    public String adicionarDeficiencia(String rg, String deficiencia) {
        Aluno aluno = encontrarAlunoPorRg(rg);

        if (aluno.getHistoricoSaude().getDeficiencias().contains(deficiencia)) {
            throw new AlreadyRegisteredException(deficiencia + " ja existe no historico de saude");
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
        Update update = new Update().push(HISTORICO_SAUDE + "deficiencias", deficiencia);
        mongoTemplate.updateFirst(query, update, Aluno.class);
        return deficiencia;
    }

    @Override
    public String removerDeficiencia(String rg, String deficiencia) {
        Aluno aluno = encontrarAlunoPorRg(rg);

        if (!aluno.getHistoricoSaude().getDeficiencias().contains(deficiencia)) {
            throw new EntityNotFoundException(deficiencia + " nao existe no historico de saude");
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
        Update update = new Update().pull(HISTORICO_SAUDE + "deficiencias", deficiencia);
        mongoTemplate.updateFirst(query, update, Aluno.class);
        return deficiencia;
    }

    @Override
    public String adicionarAcompanhamentoSaude(String rg, String acompanhamentoSaude) {
        Aluno aluno = encontrarAlunoPorRg(rg);

        if (aluno.getHistoricoSaude().getAcompanhamentoSaude().contains(acompanhamentoSaude)) {
            throw new AlreadyRegisteredException(acompanhamentoSaude + " ja existe no historico de saude");
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
        Update update = new Update().push(HISTORICO_SAUDE + "acompanhamentoSaude", acompanhamentoSaude);
        mongoTemplate.updateFirst(query, update, Aluno.class);
        return acompanhamentoSaude;
    }

    @Override
    public String removerAcompanhamentoSaude(String rg, String acompanhamentoSaude) {
        Aluno aluno = encontrarAlunoPorRg(rg);

        if (!aluno.getHistoricoSaude().getAcompanhamentoSaude().contains(acompanhamentoSaude)) {
            throw new EntityNotFoundException(acompanhamentoSaude + " nao existe no historico de saude");
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
        Update update = new Update().pull(HISTORICO_SAUDE + "acompanhamentoSaude", acompanhamentoSaude);
        mongoTemplate.updateFirst(query, update, Aluno.class);
        return acompanhamentoSaude;
    }

    @Override
    public Object editarHistoricoDeSaude(String rg, String campo, String historicoDeSaude, boolean resposta) {
        Aluno aluno = encontrarAlunoPorRg(rg);
        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
        Update update = new Update().set(HISTORICO_SAUDE + campo + ".resposta", resposta)
                .set(HISTORICO_SAUDE + campo + ".tipo", historicoDeSaude);
        mongoTemplate.updateFirst(query, update, Aluno.class);
        return Map.of("resposta", resposta, "tipo", historicoDeSaude);
    }

    @Override
    public String editarAlunoPorRg(String rg, UpdateAlunoDTORequest updateAlunoDTORequest) {
        Aluno alunoEncontrado = encontrarAlunoPorRg(rg);

        // Dados Escolares
        DadosEscolares dadosEscolares = DadosEscolaresMapper.setDadosEscolares(updateAlunoDTORequest.dadosEscolares(),
                alunoEncontrado);
        // Endereco
        Endereco endereco = EnderecoMapper.setEndereco(updateAlunoDTORequest.endereco(), alunoEncontrado);
        // Dados Sociais
        DadosSociais dadosSociais = DadosSociaisMapper.setDadosSociais(updateAlunoDTORequest.dadosSociais(),
                alunoEncontrado);

        alunoEncontrado.setDadosSociais(dadosSociais);
        alunoEncontrado.setEndereco(endereco);
        alunoEncontrado.setDadosEscolares(dadosEscolares);

        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(alunoEncontrado.getRg()));
        Update update = new Update();

        update.set("dadosSociais", alunoEncontrado.getDadosSociais());
        update.set("endereco", alunoEncontrado.getEndereco());
        update.set("dadosEscolares", alunoEncontrado.getDadosEscolares());

        this.mongoTemplate.updateFirst(query, update, Aluno.class);

        return "Aluno editado com sucesso!";
    }

    @Override
    public GraduacaoDTOResponse aprovarAluno(String rg) {
        Aluno alunoEncontrado = encontrarAlunoPorRg(rg);
        Graduacao graduacaoAtual = alunoEncontrado.getGraduacao().get(alunoEncontrado.getGraduacao().size() - 1);
        graduacaoAtual.aprovacao();
        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(alunoEncontrado.getRg()));
        Update update = new Update();
        update.set(GRADUACAO, alunoEncontrado.getGraduacao());
        this.mongoTemplate.updateFirst(query, update, Aluno.class);

        adicionarNovaGraduacao(alunoEncontrado.getRg(), graduacaoAtual.getKyu() - 1, graduacaoAtual.getDan());

        return GraduacaoMapper.mapToGraduacaoDTOResponse(
                alunoEncontrado.getGraduacao().get(alunoEncontrado.getGraduacao().size() - 1));
    }

    @Override
    public GraduacaoDTOResponse reprovarAluno(String rg) {
        Aluno alunoEncontrado = encontrarAlunoPorRg(rg);
        Graduacao graduacaoAtual = alunoEncontrado.getGraduacao().get(alunoEncontrado.getGraduacao().size() - 1);
        graduacaoAtual.reprovacao();
        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(alunoEncontrado.getRg()));
        Update update = new Update();
        update.set(GRADUACAO, alunoEncontrado.getGraduacao());
        this.mongoTemplate.updateFirst(query, update, Aluno.class);
        adicionarNovaGraduacao(alunoEncontrado.getRg(), graduacaoAtual.getKyu(), graduacaoAtual.getDan() - 1);
        return GraduacaoMapper.mapToGraduacaoDTOResponse(
                alunoEncontrado.getGraduacao().get(alunoEncontrado.getGraduacao().size() - 1));
    }

    @Cacheable(value = "aluno", key = "#rg")
    public Aluno encontrarAlunoPorRg(String rgAluno) {
        return alunoRepositorio.findByRg(rgAluno)
                .orElseThrow(() -> new EntityNotFoundException("Rg: " + rgAluno + " não encontrado"));
    }

    protected Optional<Responsavel> encontrarResponsavelPorCpf(Aluno aluno, String cpf) {
        return aluno.getResponsaveis().stream()
                .filter(responsaveis -> responsaveis.getCpf().equals(cpf))
                .findFirst();
    }

    protected Falta encontrarFaltasDoAluno(Aluno aluno, String faltasId) {
        Optional<Falta> faltaEncontrada = aluno.getGraduacao().get(aluno.getGraduacao().size() - 1).getFaltas().stream()
                .filter(falta -> falta.getData().equals(faltasId)).findFirst();

        return faltaEncontrada
                .orElseThrow(() -> new EntityNotFoundException("Falta com id " + faltasId + " não foi encontrada."));
    }

    protected boolean checarSeFaltaEstaRegistrada(Aluno aluno, Date data) {

        int graduacaoAtual = aluno.getGraduacao().size();

        String dataFormatada = new SimpleDateFormat(DATA_FORMATO).format(data);

        if (aluno.getGraduacao().get(graduacaoAtual - 1).getFaltas().isEmpty()) {
            return false;
        }

        return aluno.getGraduacao().get(graduacaoAtual - 1).getFaltas().stream()
                .anyMatch(falta -> falta.getData().equals(dataFormatada));
    }

    protected void mudarStatusGraduacaoAluno(Aluno aluno, boolean status) {
        int graduacaoAtual = aluno.getGraduacao().size();
        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
        Update update = new Update().set(GRADUACAO + "." + (graduacaoAtual - 1) + ".status", status);
        mongoTemplate.updateFirst(query, update, Aluno.class);
    }

    public void adicionarNovaGraduacao(String rg, int kyu, int danAtual) {
        Graduacao novaGraduacao = new Graduacao(kyu);

        if (kyu <= 1) {
            novaGraduacao.setDan(danAtual + 1);
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(rg));
        Update update = new Update();
        update.addToSet(GRADUACAO, novaGraduacao);
        this.mongoTemplate.updateFirst(query, update, Aluno.class);
    }

    @Override
    public String editarHistoricoDeSaude(String rg, UpdateHistoricoSaudeDTORequest updateHistoricoSaudeDTORequest) {
        Aluno aluno = encontrarAlunoPorRg(rg);

        aluno.getHistoricoSaude().setUsoMedicamentoContinuo(
            new UsoMedicamentoContinuo(updateHistoricoSaudeDTORequest.usoMedicamentoContinuo().tipo())
        );

        aluno.getHistoricoSaude().setAlergia(
            new Alergia(updateHistoricoSaudeDTORequest.alergia().tipo())
        );

        aluno.getHistoricoSaude().setCirurgia(
            new Cirurgia(updateHistoricoSaudeDTORequest.cirurgia().tipo())
        );

        aluno.getHistoricoSaude().setDoencaCronica(
            new DoencaCronica(updateHistoricoSaudeDTORequest.doencaCronica().tipo())
        );

        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
        Update update = new Update();
        update.set(HISTORICO_SAUDE + "usoMedicamentoContinuo", aluno.getHistoricoSaude().getUsoMedicamentoContinuo());
        update.set(HISTORICO_SAUDE + "alergia", aluno.getHistoricoSaude().getAlergia());
        update.set(HISTORICO_SAUDE + "cirurgia", aluno.getHistoricoSaude().getCirurgia());
        update.set(HISTORICO_SAUDE + "doencaCronica", aluno.getHistoricoSaude().getDoencaCronica());
        mongoTemplate.updateFirst(query, update, Aluno.class);
        return "Historico de saude editado com sucesso";
     }
}