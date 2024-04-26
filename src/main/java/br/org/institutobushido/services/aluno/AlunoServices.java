package br.org.institutobushido.services.aluno;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import br.org.institutobushido.controllers.dtos.aluno.AlunoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.AlunoDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.UpdateAlunoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.GraduacaoDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.faltas.FaltaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.UpdateHistoricoSaudeDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.responsavel.ResponsavelDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.responsavel.ResponsavelDTOResponse;
import br.org.institutobushido.mappers.aluno.AlunoMapper;
import br.org.institutobushido.mappers.aluno.DadosEscolaresMapper;
import br.org.institutobushido.mappers.aluno.DadosSociaisMapper;
import br.org.institutobushido.mappers.aluno.EnderecoMapper;
import br.org.institutobushido.mappers.aluno.GraduacaoMapper;
import br.org.institutobushido.mappers.aluno.ResponsavelMapper;
import br.org.institutobushido.models.aluno.Aluno;
import br.org.institutobushido.models.aluno.dados_escolares.DadosEscolares;
import br.org.institutobushido.models.aluno.dados_sociais.DadosSociais;
import br.org.institutobushido.models.aluno.endereco.Endereco;
import br.org.institutobushido.models.aluno.graduacao.Graduacao;
import br.org.institutobushido.models.aluno.graduacao.falta.Falta;
import br.org.institutobushido.models.aluno.historico_de_saude.informacoes_saude.Alergia;
import br.org.institutobushido.models.aluno.historico_de_saude.informacoes_saude.Cirurgia;
import br.org.institutobushido.models.aluno.historico_de_saude.informacoes_saude.DoencaCronica;
import br.org.institutobushido.models.aluno.historico_de_saude.informacoes_saude.UsoMedicamentoContinuo;
import br.org.institutobushido.models.aluno.responsaveis.Responsavel;
import br.org.institutobushido.repositories.AlunoRepositorio;
import br.org.institutobushido.resources.exceptions.AlreadyRegisteredException;
import br.org.institutobushido.resources.exceptions.EntityNotFoundException;

@Service
public class AlunoServices implements AlunoServicesInterface {

    private final AlunoRepositorio alunoRepositorio;
    private final MongoTemplate mongoTemplate;

    public AlunoServices(AlunoRepositorio alunoRepositorio, MongoTemplate mongoTemplate) {
        this.alunoRepositorio = alunoRepositorio;
        this.mongoTemplate = mongoTemplate;
    }

    private static final String GRADUACAO = "graduacao";
    private static final String HISTORICO_SAUDE = "historicoSaude.";

    @Override
    public String adicionarAluno(AlunoDTORequest alunoDTORequest) {

        Optional<Aluno> alunoEncontrado = alunoRepositorio.findByRg(alunoDTORequest.rg());

        if (alunoEncontrado.isPresent()) {
            throw new AlreadyRegisteredException("O Aluno com o rg " + alunoDTORequest.rg() + " ja esta cadastrado!");
        }

        Aluno novoAluno = alunoRepositorio.save(AlunoMapper.mapToAluno(alunoDTORequest));

        return novoAluno.getRg();
    }

    @Override
    public List<AlunoDTOResponse> buscarAluno(String nome, String rg, int pagina, int tamanho, String ordenarPor,
            String sequenciaOrdenacao) {
        if (rg != null) {
            return this.buscarAlunoPorRg(rg);
        }
        return this.buscarAlunosPorNome(nome, pagina, tamanho, ordenarPor, sequenciaOrdenacao);
    }

    @Override
    public ResponsavelDTOResponse adicionarResponsavel(String rg, ResponsavelDTORequest responsavelDTORequest) {
        Aluno aluno = encontrarAlunoPorRg(rg);
        Responsavel novoResponsavel = aluno
                .adicionarResponsavel(ResponsavelMapper.mapToResponsavel(responsavelDTORequest));

        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
        Update update = new Update().push("responsaveis", novoResponsavel);
        mongoTemplate.updateFirst(query, update, Aluno.class);

        return ResponsavelDTOResponse.builder()
                .withCpf(novoResponsavel.getCpf())
                .withEmail(novoResponsavel.getEmail())
                .withTelefone(novoResponsavel.getTelefone())
                .withNome(novoResponsavel.getNome())
                .withFiliacao(novoResponsavel.getFiliacao().name())
                .build();

    }

    @Override
    public String removerResponsavel(String rg, String cpf) {
        Aluno aluno = encontrarAlunoPorRg(rg);
        String cpfRemovido = aluno.removerResponsavel(cpf);
        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
        Update update = new Update().pull("responsaveis", Query.query(Criteria.where("cpf").is(cpfRemovido)));
        mongoTemplate.updateFirst(query, update, Aluno.class);
        return String.valueOf(aluno.getResponsaveis().size());
    }

    @Override
    public String adicionarFaltaDoAluno(String rg, FaltaDTORequest falta, long dataFalta) {
        Aluno aluno = encontrarAlunoPorRg(rg);
        Falta novaFalta = aluno.getGraduacaoAtual().adicionarFalta(falta.motivo(), falta.observacao(),
                dataFalta);

        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
        Update update = new Update().addToSet(GRADUACAO + "." + (aluno.getGraduacaoAtualIndex()) + ".faltas", novaFalta);
        mongoTemplate.updateFirst(query, update, Aluno.class);

        if (aluno.getGraduacaoAtual().getFaltas().size() == 5) {
            mudarStatusGraduacaoAluno(aluno, false);
        }

        return String.valueOf(aluno.getGraduacaoAtual().getFaltas().size());
    }

    @Override
    public String retirarFaltaDoAluno(String rg, String faltasId) {
        Aluno aluno = encontrarAlunoPorRg(rg);
        Falta faltaDoAluno = aluno.getGraduacaoAtual().removerFalta(faltasId);

        if (aluno.getGraduacaoAtual().getFaltas().size() == 4) {
            mudarStatusGraduacaoAluno(aluno, true);
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
        Update update = new Update().pull(GRADUACAO + "." + (aluno.getGraduacaoAtualIndex()) +
                ".faltas", faltaDoAluno);
        mongoTemplate.updateFirst(query, update, Aluno.class);
        return String.valueOf(aluno.getGraduacaoAtual().getFaltas().size());
    }

    @Override
    public String adicionarDeficiencia(String rg, String deficiencia) {
        Aluno aluno = encontrarAlunoPorRg(rg);
        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
        Update update = new Update().push(HISTORICO_SAUDE + "deficiencias",
                aluno.getHistoricoSaude().adiconarDeficiencia(deficiencia));
        mongoTemplate.updateFirst(query, update, Aluno.class);
        return deficiencia;
    }

    @Override
    public String removerDeficiencia(String rg, String deficiencia) {
        Aluno aluno = encontrarAlunoPorRg(rg);
        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
        Update update = new Update().pull(HISTORICO_SAUDE + "deficiencias",
                aluno.getHistoricoSaude().removerDeficiencia(deficiencia));
        mongoTemplate.updateFirst(query, update, Aluno.class);
        return deficiencia;
    }

    @Override
    public String adicionarAcompanhamentoSaude(String rg, String acompanhamentoSaude) {
        Aluno aluno = encontrarAlunoPorRg(rg);
        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
        Update update = new Update().push(HISTORICO_SAUDE + "acompanhamentoSaude",
                aluno.getHistoricoSaude().adicionarAcompanhamento(acompanhamentoSaude));
        mongoTemplate.updateFirst(query, update, Aluno.class);
        return acompanhamentoSaude;
    }

    @Override
    public String removerAcompanhamentoSaude(String rg, String acompanhamentoSaude) {
        Aluno aluno = encontrarAlunoPorRg(rg);
        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
        Update update = new Update().pull(HISTORICO_SAUDE + "acompanhamentoSaude",
                aluno.getHistoricoSaude().removerAcompanhamento(acompanhamentoSaude));
        mongoTemplate.updateFirst(query, update, Aluno.class);
        return acompanhamentoSaude;
    }

    @Override
    @CachePut(value = "aluno", key = "#rg")
    public String editarAlunoPorRg(String rg, UpdateAlunoDTORequest updateAlunoDTORequest) {
        Aluno alunoEncontrado = encontrarAlunoPorRg(rg);

        // Nome
        alunoEncontrado.setNome(updateAlunoDTORequest.nome());

        //  Data de Nascimento
        alunoEncontrado.setDataNascimento(new Date(updateAlunoDTORequest.dataNascimento()));

        // Genero
        alunoEncontrado.setGenero(updateAlunoDTORequest.genero());

        // Turma
        alunoEncontrado.setTurma(updateAlunoDTORequest.turma());

        // Dados Escolares
        DadosEscolares dadosEscolares = DadosEscolaresMapper.updateDadosEscolares(
                updateAlunoDTORequest.dadosEscolares(),
                alunoEncontrado);

        // Endereco
        Endereco endereco = EnderecoMapper.updateEndereco(updateAlunoDTORequest.endereco(), alunoEncontrado);

        // Dados Sociais
        DadosSociais dadosSociais = DadosSociaisMapper.updateDadosSociais(updateAlunoDTORequest.dadosSociais(),
                alunoEncontrado);

        // Historico de Saude
        this.editarHistoricoDeSaude(updateAlunoDTORequest.historicoDeSaude(), alunoEncontrado);

        alunoEncontrado.setDadosSociais(dadosSociais);
        alunoEncontrado.setEndereco(endereco);
        alunoEncontrado.setDadosEscolares(dadosEscolares);

        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(alunoEncontrado.getRg()));
        Update update = new Update();

        update.set("nome", alunoEncontrado.getNome());
        update.set("genero", alunoEncontrado.getGenero());
        update.set("dataNascimento", alunoEncontrado.getDataNascimento());
        update.set("turma", alunoEncontrado.getTurma());
        update.set("dadosSociais", alunoEncontrado.getDadosSociais());
        update.set("endereco", alunoEncontrado.getEndereco());
        update.set("dadosEscolares", alunoEncontrado.getDadosEscolares());

        this.mongoTemplate.updateFirst(query, update, Aluno.class);

        return "Aluno editado com sucesso!";
    }

    @Override
    public GraduacaoDTOResponse aprovarAluno(String rg, int notaDaProva) {
        Aluno alunoEncontrado = encontrarAlunoPorRg(rg);
        Graduacao graduacaoAtual = alunoEncontrado.getGraduacaoAtual().aprovacao(notaDaProva);

        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(alunoEncontrado.getRg()));
        Update update = new Update();
        update.set(GRADUACAO + "." + (alunoEncontrado.getGraduacaoAtualIndex()), graduacaoAtual);
        this.mongoTemplate.updateFirst(query, update, Aluno.class);

        adicionarNovaGraduacao(alunoEncontrado.getRg(), graduacaoAtual.getKyu(), graduacaoAtual.getDan());

        return GraduacaoMapper.mapToGraduacaoDTOResponse(
                alunoEncontrado.getGraduacao().get(alunoEncontrado.getGraduacao().size() - 1));
    }

    @Override
    public GraduacaoDTOResponse reprovarAluno(String rg, int notaDaProva) {
        Aluno alunoEncontrado = encontrarAlunoPorRg(rg);
        Graduacao graduacaoAtual = alunoEncontrado.getGraduacaoAtual();
        graduacaoAtual.reprovacao(notaDaProva);
        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(alunoEncontrado.getRg()));
        Update update = new Update();
        update.set(GRADUACAO, alunoEncontrado.getGraduacao());
        this.mongoTemplate.updateFirst(query, update, Aluno.class);
        adicionarNovaGraduacao(alunoEncontrado.getRg(), graduacaoAtual.getKyu(), graduacaoAtual.getDan() - 1);
        return GraduacaoMapper.mapToGraduacaoDTOResponse(
                alunoEncontrado.getGraduacao().get(alunoEncontrado.getGraduacao().size() - 1));
    }

    protected void mudarStatusGraduacaoAluno(Aluno aluno, boolean status) {
        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
        Update update = new Update().set(GRADUACAO + "." + (aluno.getGraduacaoAtualIndex()) + ".status", status);
        mongoTemplate.updateFirst(query, update, Aluno.class);
    }

    public void adicionarNovaGraduacao(String rg, int kyu, int danAtual) {
        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(rg));
        Update update = new Update();
        update.push(GRADUACAO, Graduacao.gerarNovaGraduacao(kyu, danAtual));
        this.mongoTemplate.updateFirst(query, update, Aluno.class);
    }

    private void editarHistoricoDeSaude(UpdateHistoricoSaudeDTORequest updateHistoricoSaudeDTORequest, Aluno aluno) {

        if (updateHistoricoSaudeDTORequest == null) {
            return;
        }

        aluno.getHistoricoSaude().setTipoSanguineo(
                updateHistoricoSaudeDTORequest.tipoSanguineo());

        aluno.getHistoricoSaude().setUsoMedicamentoContinuo(
                new UsoMedicamentoContinuo(updateHistoricoSaudeDTORequest.usoMedicamentoContinuo().tipo()));

        aluno.getHistoricoSaude().setAlergia(
                new Alergia(updateHistoricoSaudeDTORequest.alergia().tipo()));

        aluno.getHistoricoSaude().setCirurgia(
                new Cirurgia(updateHistoricoSaudeDTORequest.cirurgia().tipo()));

        aluno.getHistoricoSaude().setDoencaCronica(
                new DoencaCronica(updateHistoricoSaudeDTORequest.doencaCronica().tipo()));

        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
        Update update = new Update();
        update.set(HISTORICO_SAUDE + "tipoSanguineo", aluno.getHistoricoSaude().getTipoSanguineo());
        update.set(HISTORICO_SAUDE + "usoMedicamentoContinuo", aluno.getHistoricoSaude().getUsoMedicamentoContinuo());
        update.set(HISTORICO_SAUDE + "alergia", aluno.getHistoricoSaude().getAlergia());
        update.set(HISTORICO_SAUDE + "cirurgia", aluno.getHistoricoSaude().getCirurgia());
        update.set(HISTORICO_SAUDE + "doencaCronica", aluno.getHistoricoSaude().getDoencaCronica());
        mongoTemplate.updateFirst(query, update, Aluno.class);
    }

    protected Aluno encontrarAlunoPorRg(String rgAluno) {
        List<AlunoDTOResponse> alunoEncontrado = buscarAlunoPorRg(rgAluno);

        if (alunoEncontrado.isEmpty()) {
            throw new EntityNotFoundException("Aluno com o rg " + rgAluno + " nao encontrado!");
        }

        return AlunoMapper.mapToAluno(alunoEncontrado.get(0));
    }

    @Cacheable(value = "aluno", key = "#rg")
    public List<AlunoDTOResponse> buscarAlunoPorRg(String rg) {
        Query query = new Query(Criteria.where("rg").is(rg));
        return AlunoMapper.mapToListAlunoDTOResponse(mongoTemplate.find(query, Aluno.class));
    }

    private List<AlunoDTOResponse> buscarAlunosPorNome(String nome, int page, int size, String sortOrder,
            String sortBy) {
        Direction direction = sortOrder.equalsIgnoreCase("asc") ? Direction.ASC : Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort.and(Sort.by("dataPreenchimento")));
        Query query = new Query(Criteria.where("nome").regex(nome, "si")).with(pageable);
        return AlunoMapper.mapToListAlunoDTOResponse(mongoTemplate.find(query, Aluno.class));
    }
}