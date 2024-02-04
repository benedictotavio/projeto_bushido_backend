package br.org.institutobushido.model.aluno;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.org.institutobushido.model.aluno.objects.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import br.org.institutobushido.enums.Genero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "alunos")
public class Aluno implements AlunoInterface {
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

    @Override
    public String toString() {
        return "Aluno [nome=" + nome + ", dataNascimento=" + dataNascimento + ", genero=" + genero + ", rg=" + rg
                + ", dadosEscolares=" + dadosEscolares + ", dataPreenchimento=" + dataPreenchimento + ", endereco="
                + endereco + ", dadosSociais=" + dadosSociais + ", responsaveis=" + responsaveis + ", graduacao="
                + graduacao + ", historicoSaude=" + historicoSaude + "]";
    }
}
