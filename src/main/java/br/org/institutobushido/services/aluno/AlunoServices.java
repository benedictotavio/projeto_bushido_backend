package br.org.institutobushido.services.aluno;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import br.org.institutobushido.controllers.dtos.aluno.AlunoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.AlunoDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.faltas.FaltaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.responsavel.ResponsavelDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.responsavel.ResponsavelDTOResponse;
import br.org.institutobushido.mappers.DadosEscolaresMapper;
import br.org.institutobushido.mappers.DadosSociaisMapper;
import br.org.institutobushido.mappers.EnderecoMapper;
import br.org.institutobushido.mappers.GraduacaoMapper;
import br.org.institutobushido.mappers.HistoricoSaudeMapper;
import br.org.institutobushido.mappers.ResponsavelMapper;
import br.org.institutobushido.model.aluno.Aluno;
import br.org.institutobushido.model.aluno.graduacao.falta.Falta;
import br.org.institutobushido.model.aluno.responsaveis.Responsavel;
import br.org.institutobushido.repositories.AlunoRepositorio;
import br.org.institutobushido.resources.exceptions.AlreadyRegisteredException;
import br.org.institutobushido.resources.exceptions.EntityNotFoundException;
import br.org.institutobushido.resources.exceptions.InactiveUserException;
import br.org.institutobushido.resources.exceptions.LimitQuantityException;

@Service
public class AlunoServices implements AlunoServicesInterface {

    @Autowired
    private AlunoRepositorio alunoRepositorio;
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String GRADUACAO_FALTA = "graduacao.faltas";
    private static final String DATA_FORMATO = "dd-MM-yyyy";
    private static final String HISTORICO_SAUDE = "historicoSaude.";

    @Override
    public AlunoDTOResponse adicionarAluno(AlunoDTORequest alunoDTORequest) {

        Optional<Aluno> alunoEncontrado = alunoRepositorio.findByRg(alunoDTORequest.rg());

        if (alunoEncontrado.isPresent()) {
            throw new AlreadyRegisteredException("O Aluno com o rg " + alunoDTORequest.rg() + " ja esta cadastrado!");
        }
        Aluno aluno = new Aluno();
        aluno.setGenero(alunoDTORequest.genero());
        aluno.setDataNascimento(alunoDTORequest.dataNascimento());
        aluno.setNome(alunoDTORequest.nome());
        aluno.setRg(alunoDTORequest.rg());
        aluno.setGraduacao(GraduacaoMapper.mapToGraduacao(alunoDTORequest.graduacao()));
        aluno.setResponsaveis(ResponsavelMapper.mapToResponsaveis(alunoDTORequest.responsaveis()));
        aluno.setEndereco(EnderecoMapper.mapToEndereco(alunoDTORequest.endereco()));
        aluno.setDadosSociais(DadosSociaisMapper.mapToDadosSociais(alunoDTORequest.dadosSociais()));
        aluno.setDadosEscolares(DadosEscolaresMapper.mapToDadosEscolares(alunoDTORequest.dadosEscolares()));
        aluno.setHistoricoSaude(HistoricoSaudeMapper.mapToHistoricoSaude(alunoDTORequest.historicoSaude()));
        
        Aluno novoAluno = alunoRepositorio.save(aluno);

        return AlunoDTOResponse.builder()
                .withNome(novoAluno.getNome())
                .withGenero(novoAluno.getGenero())
                .withDataNascimento(novoAluno.getDataNascimento())
                .withDataPreenchimento(novoAluno.getDataPreenchimento())
                .withRg(novoAluno.getRg())
                .withResponsaveis(ResponsavelMapper.mapToResponsaveisDTOResponse(novoAluno.getResponsaveis()))
                .withEndereco(EnderecoMapper.mapToEnderecoDTOResponse(novoAluno.getEndereco()))
                .withDadosSociais(DadosSociaisMapper.mapToDadosSociaisDTOResponse(novoAluno.getDadosSociais()))
                .withDadosEscolares(
                        DadosEscolaresMapper.mapToDadosEscolaresDTOResponse(novoAluno.getDadosEscolares()))
                .withGraduacao(GraduacaoMapper.mapToGraduacaoDTOResponse(novoAluno.getGraduacao()))
                .withHistoricoSaude(
                        HistoricoSaudeMapper.mapToHistoricoSaudeDTOResponse(novoAluno.getHistoricoSaude()))
                .build();
    }

    @Override
    public AlunoDTOResponse buscarAlunoPorRg(String rg) {
        Aluno alunoEncontrado = encontrarAlunoPorRg(rg);

        return AlunoDTOResponse.builder()
                .withNome(alunoEncontrado.getNome())
                .withGenero(alunoEncontrado.getGenero())
                .withDataNascimento(alunoEncontrado.getDataNascimento())
                .withDadosSociais(DadosSociaisMapper.mapToDadosSociaisDTOResponse(alunoEncontrado.getDadosSociais()))
                .withDataPreenchimento(alunoEncontrado.getDataPreenchimento())
                .withRg(alunoEncontrado.getRg())
                .withResponsaveis(ResponsavelMapper.mapToResponsaveisDTOResponse(alunoEncontrado.getResponsaveis()))
                .withEndereco(EnderecoMapper.mapToEnderecoDTOResponse(alunoEncontrado.getEndereco()))
                .withDadosEscolares(
                        DadosEscolaresMapper.mapToDadosEscolaresDTOResponse(alunoEncontrado.getDadosEscolares()))
                .withGraduacao(GraduacaoMapper.mapToGraduacaoDTOResponse(alunoEncontrado.getGraduacao()))
                .withHistoricoSaude(
                        HistoricoSaudeMapper.mapToHistoricoSaudeDTOResponse(alunoEncontrado.getHistoricoSaude()))
                .build();
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
    public String adicionarFaltaDoAluno(String rg, FaltaDTORequest falta) {
        Aluno aluno = encontrarAlunoPorRg(rg);

        if (!aluno.getGraduacao().isStatus()) {
            throw new InactiveUserException("O Aluno esta inativo. Pois o mesmo se encontra com mais de 5 faltas");
        }

        boolean faltasDoAluno = checarSeFaltaEstaRegistrada(aluno, new Date());

        if (faltasDoAluno) {
            throw new AlreadyRegisteredException("Ja existe um registro de falta nessa data");
        }

        

        Falta novaFalta = new Falta(falta.motivo(), falta.observacao());

        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
        Update update = new Update().addToSet(GRADUACAO_FALTA, novaFalta);
        mongoTemplate.updateFirst(query, update, Aluno.class);

        if (aluno.getGraduacao().getFaltas().size() == 4) {
            mudarStatusGraduacaoAluno(aluno, false);
        }

        return String.valueOf(aluno.getGraduacao().getFaltas().size() + 1);
    }

    @Override
    public String adicionarFaltaDoAluno(String rg, FaltaDTORequest falta, long dataFalta) {
        Aluno aluno = encontrarAlunoPorRg(rg);

        if (dataFalta > new Date().getTime()) {
            throw new LimitQuantityException("A data deve ser menor ou igual a data atual");
        }

        if (!aluno.getGraduacao().isStatus()) {
            throw new InactiveUserException("O Aluno esta inativo. Pois o mesmo se encontra com mais de 5 faltas");
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
        Update update = new Update().addToSet(GRADUACAO_FALTA, novaFalta);
        mongoTemplate.updateFirst(query, update, Aluno.class);

        if (aluno.getGraduacao().getFaltas().size() == 4) {
            mudarStatusGraduacaoAluno(aluno, false);
        }

        return String.valueOf(aluno.getGraduacao().getFaltas().size() + 1);
    }

    @Override
    public String retirarFaltaDoAluno(String rg, String faltasId) {
        Aluno aluno = encontrarAlunoPorRg(rg);
        Falta faltasDoAluno = encontrarFaltasDoAluno(aluno, faltasId);
        if (aluno.getGraduacao().getFaltas().size() == 5) {
            mudarStatusGraduacaoAluno(aluno, true);
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
        Update update = new Update().pull(GRADUACAO_FALTA, faltasDoAluno);
        mongoTemplate.updateFirst(query, update, Aluno.class);
        return String.valueOf(aluno.getGraduacao().getFaltas().size() - 1);
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

    protected Aluno encontrarAlunoPorRg(String rgAluno) {
        return alunoRepositorio.findByRg(rgAluno)
                .orElseThrow(() -> new EntityNotFoundException("Rg: " + rgAluno + " não encontrado"));
    }

    protected Optional<Responsavel> encontrarResponsavelPorCpf(Aluno aluno, String cpf) {
        return aluno.getResponsaveis().stream()
                .filter(responsaveis -> responsaveis.getCpf().equals(cpf))
                .findFirst();
    }

    protected Falta encontrarFaltasDoAluno(Aluno aluno, String faltasId) {
        Optional<Falta> faltaEncontrada = aluno.getGraduacao().getFaltas().stream()
                .filter(falta -> falta.getData().equals(faltasId)).findFirst();

        return faltaEncontrada
                .orElseThrow(() -> new EntityNotFoundException("Falta com id " + faltasId + " não foi encontrada."));
    }

    protected Optional<Falta> encontrarFaltasDoAlunoPelaData(Aluno aluno, Date data) {

        String dataFormatada = new SimpleDateFormat(DATA_FORMATO).format(data);

        if (!checarSeFaltaEstaRegistrada(aluno, dataFormatada)) {
            throw new EntityNotFoundException("Falta" + data + "não encontrada!");
        }

        return aluno.getGraduacao().getFaltas().stream().filter(falta -> falta.getData().equals(dataFormatada))
                .findFirst();
    }

    protected boolean checarSeFaltaEstaRegistrada(Aluno aluno, String data) {

        if (aluno.getGraduacao().getFaltas().isEmpty()) {
            return false;
        }

        return aluno.getGraduacao().getFaltas().stream().anyMatch(falta -> falta.getData().equals(data));
    }

    protected boolean checarSeFaltaEstaRegistrada(Aluno aluno, Date data) {

        String dataFormatada = new SimpleDateFormat(DATA_FORMATO).format(data);

        if (aluno.getGraduacao().getFaltas().isEmpty()) {
            return false;
        }

        return aluno.getGraduacao().getFaltas().stream().anyMatch(falta -> falta.getData().equals(dataFormatada));
    }

    protected void mudarStatusGraduacaoAluno(Aluno aluno, boolean status) {
        Query query = new Query();
        query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
        Update update = new Update().set("graduacao.status", status);
        mongoTemplate.updateFirst(query, update, Aluno.class);
    }
}