package br.org.institutobushido.models.aluno.responsaveis;

import java.io.Serializable;
import br.org.institutobushido.enums.aluno.FiliacaoResposavel;
import br.org.institutobushido.utils.default_values.ValoresPadraoResponsavel;
import br.org.institutobushido.utils.resources.exceptions.InvalidFormatDataException;
import lombok.Getter;

@Getter
public class Responsavel implements Serializable {

    private static final long serialVersionUID = 2405172041950251807L;

    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private FiliacaoResposavel filiacao;

    public Responsavel(String nome, String cpf, String telefone, String email, FiliacaoResposavel filiacao) {

        if (cpf == null || cpf.length() > ValoresPadraoResponsavel.DIGITOS_MAX_CPF
                || cpf.length() < ValoresPadraoResponsavel.DIGITOS_MIN_CPF) {
            throw new InvalidFormatDataException(
                    "Cpf do responsavel" + cpf + ". siga o formato de 11 digitos: XXXXXXXXXX");
        }

        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.filiacao = filiacao;
    }

}