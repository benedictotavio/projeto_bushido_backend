package br.org.institutobushido.model.aluno;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import br.org.institutobushido.model.aluno.object.DadosEscolares;
import br.org.institutobushido.model.aluno.object.DadosSociais;
import br.org.institutobushido.model.aluno.object.Endereco;
import br.org.institutobushido.model.aluno.object.Graduacao;
import br.org.institutobushido.model.aluno.object.Responsavel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "alunos")
public class Aluno implements AlunoInterface {
    private String nome;
    private DadosEscolares dadosEscolares;
    private Date dataPreenchimento = new Date();
    private Endereco endereco;
    private DadosSociais dadosSociais;
    @Indexed(unique = true, background = true)
    private String rg;

    private List<Responsavel> responsaveis = new ArrayList<>();

    private Graduacao graduacao;
}
