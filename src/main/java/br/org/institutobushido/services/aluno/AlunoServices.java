package br.org.institutobushido.services.aluno;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;

import br.org.institutobushido.dtos.aluno.AlunoDTORequest;
import br.org.institutobushido.dtos.aluno.AlunoDTOResponse;
import br.org.institutobushido.dtos.aluno.ResponsavelDTORequest;
import br.org.institutobushido.dtos.aluno.ResponsavelDTOResponse;
import br.org.institutobushido.mappers.ResponsavelMapper;
import br.org.institutobushido.model.aluno.Aluno;
import br.org.institutobushido.model.aluno.Responsavel;
import br.org.institutobushido.repositories.AlunoRepositorio;

@Service
public class AlunoServices implements AlunoServicesInterface {

    @Autowired
    private AlunoRepositorio alunoRepositorio;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public AlunoDTOResponse adicionarAluno(AlunoDTORequest alunoDTORequest) {
        Optional<Aluno> alunoEncontrado = alunoRepositorio.findByRg(alunoDTORequest.rg());

        if (!alunoEncontrado.isPresent()) {
            Aluno aluno = new Aluno();
            aluno.setNome(alunoDTORequest.nome());
            aluno.setBolsaFamilia(alunoDTORequest.bolsaFamilia());
            aluno.setImovel(alunoDTORequest.imovel());
            aluno.setAuxilioBrasil(alunoDTORequest.auxilioBrasil());
            aluno.setNumerosDePessoasNaCasa(alunoDTORequest.numerosDePessoasNaCasa());
            aluno.setCidade(alunoDTORequest.cidade());
            aluno.setDataPreenchimento(alunoDTORequest.dataPreenchimento());
            aluno.setContribuintesDaRendaFamiliar(alunoDTORequest.contribuintesDaRendaFamiliar());
            aluno.setEstado(alunoDTORequest.estado());
            aluno.setAlunoContribuiParaRenda(alunoDTORequest.alunoContribuiParaRenda());
            aluno.setRendaFamiliarEmSalariosMinimos(alunoDTORequest.rendaFamiliarEmSalariosMinimos());
            aluno.setTransporte(alunoDTORequest.transporte());
            aluno.setVemAcompanhado(alunoDTORequest.vemAcompanhado());
            aluno.setTurno(alunoDTORequest.turno());
            aluno.setRg(alunoDTORequest.rg());
            aluno.setFaltas(alunoDTORequest.faltas());
            aluno.setActive(alunoDTORequest.status());
            aluno.setResponsaveis(ResponsavelMapper.mapToResponsaveis(alunoDTORequest.responsaveis()));

            Aluno novoAluno = alunoRepositorio.save(aluno);

            return AlunoDTOResponse.builder()
                    .withNome(novoAluno.getNome())
                    .withBolsaFamilia(novoAluno.isBolsaFamilia())
                    .withAuxilioBrasil(novoAluno.isAuxilioBrasil())
                    .withImovel(novoAluno.getImovel())
                    .withNumerosDePessoasNaCasa(novoAluno.getNumerosDePessoasNaCasa())
                    .withContribuintesDaRendaFamiliar(novoAluno.getContribuintesDaRendaFamiliar())
                    .withAlunoContribuiParaRenda(novoAluno.isAlunoContribuiParaRenda())
                    .withRendaFamiliarEmSalariosMinimos(novoAluno.getRendaFamiliarEmSalariosMinimos())
                    .withTransporte(novoAluno.getTransporte())
                    .withVemAcompanhado(novoAluno.isVemAcompanhado())
                    .withTurno(novoAluno.getTurno())
                    .withDataPreenchimento(novoAluno.getDataPreenchimento())
                    .withCidade(novoAluno.getCidade())
                    .withEstado(novoAluno.getEstado())
                    .withRg(novoAluno.getRg())
                    .withResponsaveis(ResponsavelMapper.mapToResponsaveisDTOResponse(novoAluno.getResponsaveis()))
                    .withFaltas(novoAluno.getFaltas())
                    .withStatus(novoAluno.isActive())
                    .build();
        }

        throw new MongoException("O Aluno com o rg " + alunoDTORequest.rg() + " ja esta cadastrado!");
    }

    @Override
    public AlunoDTOResponse buscarAlunoPorRg(String rg) {
        Aluno alunoEncontrado = encontrarAlunoPorRg(rg);

        return AlunoDTOResponse.builder()
                .withNome(alunoEncontrado.getNome())
                .withBolsaFamilia(alunoEncontrado.isBolsaFamilia())
                .withAuxilioBrasil(alunoEncontrado.isAuxilioBrasil())
                .withImovel(alunoEncontrado.getImovel())
                .withNumerosDePessoasNaCasa(alunoEncontrado.getNumerosDePessoasNaCasa())
                .withContribuintesDaRendaFamiliar(alunoEncontrado.getContribuintesDaRendaFamiliar())
                .withAlunoContribuiParaRenda(alunoEncontrado.isAlunoContribuiParaRenda())
                .withRendaFamiliarEmSalariosMinimos(alunoEncontrado.getRendaFamiliarEmSalariosMinimos())
                .withTransporte(alunoEncontrado.getTransporte())
                .withVemAcompanhado(alunoEncontrado.isVemAcompanhado())
                .withTurno(alunoEncontrado.getTurno())
                .withDataPreenchimento(alunoEncontrado.getDataPreenchimento())
                .withCidade(alunoEncontrado.getCidade())
                .withEstado(alunoEncontrado.getEstado())
                .withRg(alunoEncontrado.getRg())
                .withResponsaveis(ResponsavelMapper.mapToResponsaveisDTOResponse(alunoEncontrado.getResponsaveis()))
                .withFaltas(alunoEncontrado.getFaltas())
                .withStatus(alunoEncontrado.isStatus())
                .build();
    }

    @Override
    public int adicionarFaltaDoAluno(String rg) {
        Query query = new Query();
        Aluno aluno = encontrarAlunoPorRg(rg);
        aluno.adicionarFalta();
        query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
        Update update = new Update();
        update.set("faltas", aluno.getFaltas());
        mongoTemplate.findAndModify(query, update, Aluno.class);
        return aluno.getFaltas();
    }

    @Override
    public int retirarFaltaDoAluno(String rg) {
        Query query = new Query();
        Aluno aluno = encontrarAlunoPorRg(rg);
        if (aluno.getFaltas() > 0) {
            aluno.retiraFalta();
            query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
            Update update = new Update();
            update.set("faltas", aluno.getFaltas());
            mongoTemplate.findAndModify(query, update, Aluno.class);
            return aluno.getFaltas();
        }
        throw new MongoException("O aluno já não possui nenhuma falta.");
    }

    @Override
    public ResponsavelDTOResponse adicionarResponsavel(String rg, ResponsavelDTORequest responsavelDTORequest) {
        Aluno aluno = encontrarAlunoPorRg(rg);
        Optional<Responsavel> responsavel = encontrarResponsavelPorCpf(aluno, responsavelDTORequest.cpf());
        if (aluno.getResponsaveis().size() < 5 && responsavel.isEmpty()) {
            Query query = new Query();
            query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
            Update update = new Update().push("responsaveis", responsavelDTORequest);
            mongoTemplate.updateFirst(query, update, Aluno.class);
            return ResponsavelDTOResponse.builder().withCpf(responsavelDTORequest.cpf())
                    .withEmail(responsavelDTORequest.email()).withFiliacao(responsavelDTORequest.filiacao().toString())
                    .withNome(responsavelDTORequest.nome())
                    .withTelefone(responsavelDTORequest.telefone()).build();
        }
        throw new MongoException("Não foi possivel adicionar esse responsavel");
    }

    @Override
    public boolean removerResponsavel(String rg, String cpf) {
        Aluno aluno = encontrarAlunoPorRg(rg);
        Optional<Responsavel> responsavel = encontrarResponsavelPorCpf(aluno, cpf);
        if (responsavel.isPresent() && aluno.getResponsaveis().size() > 1) {
            Query query = new Query();
            query.addCriteria(Criteria.where("rg").is(aluno.getRg()));
            Update update = new Update().pull("responsaveis", Query.query(Criteria.where("cpf").is(cpf)));
            mongoTemplate.updateFirst(query, update, Aluno.class);
            return true;
        }
        throw new MongoException("O aluno deve ter pelo menos 1 responsavel!");
    }

    protected Aluno encontrarAlunoPorRg(String rgAluno) {
        return alunoRepositorio.findByRg(rgAluno)
                .orElseThrow(() -> new MongoException("Email: " + rgAluno + " não encontrado"));
    }

    protected Optional<Responsavel> encontrarResponsavelPorCpf(Aluno aluno, String cpf) {
        return aluno.getResponsaveis().stream()
                .filter(responsaveis -> responsaveis.getCpf().equals(cpf))
                .findFirst();
    }
}
