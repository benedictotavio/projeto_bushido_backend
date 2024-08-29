package br.org.institutobushido.models.aluno.endereco;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Endereco implements Serializable {
    private static final long serialVersionUID = 2405172041950251807L;
    private String cidade;
    private String estado;
    private String cep;
    private String numero;
    private String logradouro;

    public void setLogradouro(String logradouro) {
        if (logradouro == null || logradouro.isEmpty()) {
            return;
        }
        this.logradouro = logradouro;
    }

    public void setCidade(String cidade) {
        if (cidade == null || cidade.isEmpty()) {
            return;
        }
        this.cidade = cidade;
    }

    public void setEstado(String estado) {
        if (estado == null || estado.isEmpty()) {
            return;
        }
        this.estado = estado;
    }

    public void setCep(String cep) {
        if (cep == null || cep.isEmpty()) {
            return;
        }
        this.cep = cep;
    }

    public void setNumero(String numero) {
        if (numero == null || numero.isEmpty()) {
            return;
        }
        this.numero = numero;
    }
}