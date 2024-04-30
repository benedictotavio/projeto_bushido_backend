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
import br.org.institutobushido.utils.default_values.ValoresPadraoResponsavel;
import br.org.institutobushido.utils.resources.exceptions.AlreadyRegisteredException;
import br.org.institutobushido.utils.resources.exceptions.EntityNotFoundException;
import br.org.institutobushido.utils.resources.exceptions.LimitQuantityException;
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

    public void setDataNascimento(long dataNascimento) {
        if (dataNascimento == 0 || new Date(dataNascimento).after(new Date())) {
            return;
        }
        this.dataNascimento = new Date(dataNascimento);
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

    public void setHistoricoSaude(HistoricoSaude historicoSaude) {
        if (historicoSaude == null) {
            return;
        }
        this.historicoSaude = historicoSaude;
    }

    /**
     * Adiciona uma nova graduacao ao aluno
     *
     * @param graduacao Graduacao a ser adicionada
     */
    public void adicionarGraduacao(Graduacao novaGraduacao) {
        if (graduacao == null) {
            return;
        }
        this.graduacao.add(novaGraduacao);
    }

    /**
     * Adiciona um novo Responsavel ao Aluno
     *
     * @param novoResponsavel Responsavel a ser adicionado
     * @return o responsavel adicionado
     * @throws LimitQuantityException     se ja possuir 5 responsaveis
     * @throws AlreadyRegisteredException caso o responsavel ja esteja cadastrado ao
     *                                    aluno
     * @throws AlreadyRegisteredException caso o responsavel ja tenha a filiacao
     *                                    especificada
     */
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

    /**
     * Remove o responsavel do Aluno
     *
     * @param cpf CPF do responsavel
     * @return CPF do responsavel removido
     * @throws EntityNotFoundException se o responsavel nao for encontrado
     * @throws LimitQuantityException  se o Aluno tiver menos de 1 responsavel
     */
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

    /**
     * Encontra o responsavel pelo seu CPF
     *
     * @param cpf CPF do responsavel
     * @return Responsavel encontrado
     */
    private Responsavel encontrarResponsavelPorCpf(String cpf) {
        Optional<Responsavel> encontrado = this.getResponsaveis().stream()
                .filter(responsavel -> responsavel.getCpf().equals(cpf))
                .findFirst();

        return encontrado.orElse(null);
    }

    /**
     * Retorna a ultima graduacao(graduacao atual)
     *
     * @return Graduacao atual
     */
    public Graduacao getGraduacaoAtual() {
        return this.getGraduacao().get(this.getGraduacao().size() - 1);
    }

    /**
     * Retorna o index da ultima graduacao(graduacao atual)
     *
     * @return index da ultima graduacao
     */
    public int getGraduacaoAtualIndex() {
        return this.getGraduacao().size() - 1;
    }
}
