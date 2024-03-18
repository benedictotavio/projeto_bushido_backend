package br.org.institutobushido.model.aluno;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import br.org.institutobushido.enums.aluno.Genero;
import br.org.institutobushido.model.aluno.dados_escolares.DadosEscolares;
import br.org.institutobushido.model.aluno.dados_sociais.DadosSociais;
import br.org.institutobushido.model.aluno.endereco.Endereco;
import br.org.institutobushido.model.aluno.graduacao.Graduacao;
import br.org.institutobushido.model.aluno.historico_de_saude.HistoricoSaude;
import br.org.institutobushido.model.aluno.responsaveis.Responsavel;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "alunos")
public class Aluno {
    private String nome;
    private Date dataNascimento;
    private Genero genero;

    @Indexed(unique = true, background = true)
    private String rg;

    private DadosEscolares dadosEscolares;
    private Date dataPreenchimento = new Date();
    private Endereco endereco;
    private DadosSociais dadosSociais;
    private List<Responsavel> responsaveis = new ArrayList<>();
    private Graduacao graduacao;
    private HistoricoSaude historicoSaude;

    public void setRg(String rg) {
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
            dadosEscolares = this.dadosEscolares;
        }
        this.dadosEscolares = dadosEscolares;
    }

    public void setEndereco(Endereco endereco) {
        if (endereco == null) {
            endereco = this.endereco;
        }
        this.endereco = endereco;
    }

    public void setDadosSociais(DadosSociais dadosSociais) {
        if (dadosSociais == null) {
            dadosSociais = this.dadosSociais;
        }
        this.dadosSociais = dadosSociais;
    }

    public void setGraduacao(Graduacao graduacao) {
        if (graduacao == null) {
            graduacao = this.graduacao;
        }
        this.graduacao = graduacao;
    }

    public void setHistoricoSaude(HistoricoSaude historicoSaude) {
        if (historicoSaude == null) {
            return;
        }
        this.historicoSaude = historicoSaude;
    }

}
