package br.org.institutobushido.models.aluno;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import br.org.institutobushido.enums.aluno.FiliacaoResposavel;
import br.org.institutobushido.enums.aluno.Genero;
import br.org.institutobushido.models.aluno.dados_escolares.DadosEscolares;
import br.org.institutobushido.models.aluno.dados_sociais.DadosSociais;
import br.org.institutobushido.models.aluno.endereco.Endereco;
import br.org.institutobushido.models.aluno.graduacao.Graduacao;
import br.org.institutobushido.models.aluno.historico_de_saude.HistoricoSaude;
import br.org.institutobushido.models.aluno.responsaveis.Responsavel;
import br.org.institutobushido.resources.exceptions.AlreadyRegisteredException;
import br.org.institutobushido.resources.exceptions.EntityNotFoundException;
import br.org.institutobushido.resources.exceptions.LimitQuantityException;
import br.org.institutobushido.utils.ValoresPadraoResponsavel;
import lombok.Getter;

@Getter
@Document(collection = "alunos")
public class Aluno implements Serializable {

    @Serial
    private static final long serialVersionUID = 2405172041950251807L;

    @Id
    private String cpf;

    @Indexed(unique = true, background = true)
    private String nome;
    private Date dataNascimento;
    private Genero genero;
    private String turma;
    private DadosEscolares dadosEscolares;
    private Date dataPreenchimento;
    private Endereco endereco;
    private DadosSociais dadosSociais;
    private List<Responsavel> responsaveis = new ArrayList<>();
    private List<Graduacao> graduacao;
    private HistoricoSaude historicoSaude;

    public Aluno(String cpf, String nome, Date dataNascimento, Genero genero, String turma) {
        this.graduacao = new ArrayList<>();
        this.historicoSaude = new HistoricoSaude();
        this.dadosEscolares = new DadosEscolares();
        this.dadosSociais = new DadosSociais();
        this.endereco = new Endereco();
        this.responsaveis = new ArrayList<>();
        this.dataPreenchimento = new Date();
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.cpf = cpf;
        this.turma = turma;
    }

    public void setResponsaveis(List<Responsavel> responsaveis) {
        if (responsaveis.isEmpty()) {
            return;
        }
        this.responsaveis = responsaveis;
    }

    public void setTurma(String turma) {
        if (turma == null || turma.isEmpty()) {
            return;
        }
        this.turma = turma;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isEmpty()) {
           return;
        }
        this.nome = nome;
    }

    public void setDataNascimento(Date dataNascimento) {
        if (dataNascimento == null || dataNascimento.after(new Date())) {
            return;
        }
        this.dataNascimento = dataNascimento;
    }

    public void setGenero(Genero genero) {
        if (genero == null) {
            return;
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

    public void setGraduacao(List<Graduacao> graduacao) {
        if (graduacao == null) {
            return;
        }
        this.graduacao = graduacao;
    }

    public void adicionarGraduacao(Graduacao graduacao) {
        if (graduacao == null) {
            return;
        }
        this.graduacao.add(graduacao);
    }

    public Responsavel adicionarResponsavel(Responsavel novoResponsavel) {

        if (this.getResponsaveis().size() >= ValoresPadraoResponsavel.MAXIMO_DE_RESPONSAVEIS) {
            throw new LimitQuantityException("O Aluno so pode ter até 5 responsaveis!");
        }

        if (this.encontrarResponsavelPorCpf(novoResponsavel.getCpf()) != null) {
            throw new AlreadyRegisteredException(
                    "O responsavel com o cpf " + novoResponsavel.getCpf() + " ja esta cadastrado!");
        }

        if (novoResponsavel.getFiliacao() != FiliacaoResposavel.OUTRO) {
            boolean responsavelJaCadastrado = this.getResponsaveis().stream().anyMatch(
                    responsavel -> responsavel.getFiliacao().equals(novoResponsavel.getFiliacao()));
            if (responsavelJaCadastrado) {
                throw new AlreadyRegisteredException(
                        "O Aluno so pode ter um responsavel com a filiacao " + novoResponsavel.getFiliacao());
            }
        }

        this.responsaveis.add(novoResponsavel);
        return novoResponsavel;
    }

    public String removerResponsavel(String cpf) {

        if (this.getResponsaveis().size() == ValoresPadraoResponsavel.MINIMO_DE_RESPONSAVEIS) {
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

        return encontrado.orElse(null);
    }

    public Graduacao getGraduacaoAtual(){
        return this.getGraduacao().get(this.getGraduacao().size() - 1);
    }

    public int getGraduacaoAtualIndex(){
        return this.getGraduacao().size() - 1;
    }
}
