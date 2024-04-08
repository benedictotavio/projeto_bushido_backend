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

    public void setCidade(String cidade) {
        if (cidade == null) {
            cidade = this.cidade;
        }
        this.cidade = cidade;
    }

    public void setEstado(String estado) {
        if (estado == null) {
            estado = this.estado;
        }
        this.estado = estado;
    }

    public void setCep(String cep) {
        if (cep == null) {
            cep = this.cep;
        }
        this.cep = cep;
    }

    public void setNumero(String numero) {
        if (numero == null) {
            numero = this.numero;
        }
        this.numero = numero;
    }
}