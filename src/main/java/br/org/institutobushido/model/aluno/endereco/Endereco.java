package br.org.institutobushido.model.aluno.endereco;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {
    private String cidade;
    private String estado;
    private String cep;
    private String numero;
}