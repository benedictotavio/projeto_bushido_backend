package br.org.institutobushido.model.aluno;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import br.org.institutobushido.enums.aluno.FiliacaoResposavel;
import br.org.institutobushido.enums.aluno.Genero;
import br.org.institutobushido.model.aluno.dados_escolares.DadosEscolares;
import br.org.institutobushido.model.aluno.dados_sociais.DadosSociais;
import br.org.institutobushido.model.aluno.endereco.Endereco;
import br.org.institutobushido.model.aluno.graduacao.Graduacao;
import br.org.institutobushido.model.aluno.historico_de_saude.HistoricoSaude;
import br.org.institutobushido.model.aluno.responsaveis.Responsavel;
import br.org.institutobushido.resources.exceptions.AlreadyRegisteredException;
import br.org.institutobushido.resources.exceptions.EntityNotFoundException;
import br.org.institutobushido.resources.exceptions.LimitQuantityException;
import lombok.Getter;

@Getter
@Document(collection = "alunos")
public class Aluno implements Serializable {
    private static final long serialVersionUID = 2405172041950251807L;

    private String nome;
    private Date dataNascimento;
    private Genero genero;

    @Indexed(unique = true, background = true)
    private String rg;

    private DadosEscolares dadosEscolares;
    private Date dataPreenchimento;
    private Endereco endereco;
    private DadosSociais dadosSociais;
    private List<Responsavel> responsaveis = new ArrayList<>();
    private List<Graduacao> graduacao;
    private HistoricoSaude historicoSaude;

    public Aluno(String rg) {
        this.graduacao = new ArrayList<>();
        this.historicoSaude = new HistoricoSaude();
        this.dadosEscolares = new DadosEscolares();
        this.dadosSociais = new DadosSociais();
        this.endereco = new Endereco();
        this.responsaveis = new ArrayList<>();
        this.dataPreenchimento = new Date();
        this.nome = "";
        this.dataNascimento = new Date();
        this.genero = Genero.OUTRO;
        this.rg = rg;
    }

    public void setDataPreenchimento(Date dataPreenchimento) {
        this.dataPreenchimento = dataPreenchimento;
    }

    public void setResponsaveis(List<Responsavel> responsaveis) {
        this.responsaveis = responsaveis;
    }

    public void setNome(String nome) {
        if (nome == null) {
            nome = this.nome;
        }
        this.nome = nome;
    }

    public void setDataNascimento(Date dataNascimento) {
        if (dataNascimento == null) {
            dataNascimento = this.dataNascimento;
        }
        this.dataNascimento = dataNascimento;
    }

    public void setGenero(Genero genero) {
        if (genero == null) {
            genero = this.genero;
        }
        this.genero = genero;
    }

    public void setDadosEscolares(DadosEscolares dadosEscolares) {

        if (dadosEscolares == null) {
            return;
        }

        this.dadosEscolares = dadosEscolares;
    }

    public void setEndereco(Endereco endereco) {
        if (endereco == null) {
            return;
        }
        this.endereco = endereco;
    }

    public void setDadosSociais(DadosSociais dadosSociais) {
        if (dadosSociais == null) {
            return;
        }
        this.dadosSociais = dadosSociais;
    }

    public void addGraduacao(Graduacao graduacao) {
        if (graduacao == null) {
            return;
        }
        this.graduacao.add(graduacao);
    }

    public Responsavel adicionarResponsavel(Responsavel novoResponsavel) {
        if (novoResponsavel.getCpf() == null) {
            throw new LimitQuantityException("Cpf do responsavel deve ser informado");
        }

        if (this.encontrarResponsavelPorCpf(novoResponsavel.getCpf()) != null) {
            throw new AlreadyRegisteredException(
                    "O responsavel com o cpf " + novoResponsavel.getCpf() + " ja esta cadastrado!");
        }

        if (novoResponsavel.getFiliacao() == FiliacaoResposavel.PAI
                || novoResponsavel.getFiliacao() == FiliacaoResposavel.MAE) {
            for (Responsavel responsavel : this.getResponsaveis()) {
                if (responsavel.getFiliacao() == FiliacaoResposavel.PAI
                        || responsavel.getFiliacao() == FiliacaoResposavel.MAE) {
                    throw new LimitQuantityException("O responsavel deve ter apenas um pai ou uma mae");
                }
            }
        }

        this.responsaveis.add(novoResponsavel);
        return novoResponsavel;
    }

    public String removerResponsavel(String cpf) {

        if (this.getResponsaveis().size() == 1) {
            throw new LimitQuantityException("O aluno deve ter pelo menos 1 responsavel!");
        }

        Responsavel responsavel = this.encontrarResponsavelPorCpf(cpf);

        if (responsavel == null) {
            throw new EntityNotFoundException("O responsavel com o cpf " + cpf + " nao foi encontrado");
        }

        this.responsaveis.remove(responsavel);
        return responsavel.getCpf();
    }

    public void setHistoricoSaude(HistoricoSaude historicoSaude) {
        if (historicoSaude == null) {
            return;
        }
        this.historicoSaude = historicoSaude;
    }

    private Responsavel encontrarResponsavelPorCpf(String cpf) {
        Optional<Responsavel> encontrado = this.getResponsaveis().stream()
                .filter(responsavel -> responsavel.getCpf().equals(cpf))
                .findFirst();

        if (encontrado.isPresent()) {
            return encontrado.get();
        }

        return null;
    }
}
