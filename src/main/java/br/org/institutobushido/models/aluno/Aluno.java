package br.org.institutobushido.models.aluno;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

import br.org.institutobushido.models.aluno.imagem_aluno.ImagemAluno;
import br.org.institutobushido.providers.utils.default_values.ValoresPadraoMatricula;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import br.org.institutobushido.models.aluno.dados_escolares.DadosEscolares;
import br.org.institutobushido.models.aluno.dados_sociais.DadosSociais;
import br.org.institutobushido.models.aluno.endereco.Endereco;
import br.org.institutobushido.models.aluno.graduacao.Graduacao;
import br.org.institutobushido.models.aluno.historico_de_saude.HistoricoSaude;
import br.org.institutobushido.models.aluno.responsaveis.Responsavel;
import br.org.institutobushido.providers.enums.aluno.CorDePele;
import br.org.institutobushido.providers.enums.aluno.FiliacaoResposavel;
import br.org.institutobushido.providers.enums.aluno.Genero;
import br.org.institutobushido.providers.utils.default_values.ValoresPadraoResponsavel;
import br.org.institutobushido.providers.utils.resources.exceptions.AlreadyRegisteredException;
import br.org.institutobushido.providers.utils.resources.exceptions.EntityNotFoundException;
import br.org.institutobushido.providers.utils.resources.exceptions.LimitQuantityException;
import lombok.Getter;

@Getter
@Document(collection = "alunos")
public class Aluno implements Serializable {

    @Serial
    private static final long serialVersionUID = 2405172041950251807L;

    @Id
    private String matricula;

    @Indexed(unique = true, background = true)
    private String nome;

    private Date dataNascimento;
    private Genero genero;
    private String turma;
    private DadosEscolares dadosEscolares;
    private Date dataPreenchimento;
    private Endereco endereco;
    private DadosSociais dadosSociais;
    private List<Responsavel> responsaveis;
    private List<Graduacao> graduacao;
    private HistoricoSaude historicoSaude;
    private ImagemAluno imagemAluno;
    private CorDePele corDePele;
    private String telefone;
    private String cartaoSus;
    private String cpf;
    private String email;
    private String rg;

    public Aluno() {
        this.graduacao = new ArrayList<>();
        this.historicoSaude = new HistoricoSaude();
        this.dadosEscolares = new DadosEscolares();
        this.dadosSociais = new DadosSociais();
        this.endereco = new Endereco();
        this.responsaveis = new ArrayList<>();
        this.dataPreenchimento = new Date();
        this.imagemAluno = new ImagemAluno();
        this.responsaveis = new ArrayList<>();
    }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            return;
        }
        this.cpf = cpf;
    }

    public void setMatricula(String matricula) {
        if (matricula == null || matricula.isEmpty()) {
            return;
        }
        this.matricula = matricula;
    }

    public void setCartaoSus(String cartaoSus) {
        if (cartaoSus == null || cartaoSus.isEmpty()) {
            return;
        }
        this.cartaoSus = cartaoSus;
    }

    public void setTelefone(String telefone) {
        if (telefone == null || telefone.isEmpty()) {
            return;
        }
        this.telefone = telefone;
    }

    public void setCorDePele(CorDePele corDePele) {
        if (corDePele == null) {
            return;
        }
        this.corDePele = corDePele;
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

    public void setEmail(String email) {
        if (email == null || email.isEmpty()) {
            return;
        }
        this.email = email;
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

    public void setImagemAluno(ImagemAluno imagemAluno) {
        if (imagemAluno == null) {
            return;
        }
        this.imagemAluno = imagemAluno;
    }

    public void setRg(String rg) {
        if (rg == null || rg.isEmpty()) {
            return;
        }
        this.rg = rg;
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

    public static String gerarMatricula() {
        Random rand = new Random();
        int max= ValoresPadraoMatricula.NUMERO_MAXIMO_MATRICULA;
        int min=ValoresPadraoMatricula.NUMERO_MINIMO_MATRICULA;
        Calendar cal = Calendar.getInstance();
        String ano = Integer.toString(cal.get(Calendar.YEAR));
        String seqAleatoria = Integer.toString(rand.nextInt(max - min + 1) + min);
        String matricula = ano + seqAleatoria;

        return matricula;
    }
}
