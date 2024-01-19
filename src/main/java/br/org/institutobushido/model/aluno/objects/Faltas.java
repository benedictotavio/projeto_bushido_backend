package br.org.institutobushido.model.aluno.objects;

import java.util.Date;

import lombok.Data;

@Data
public class Faltas {
    private String motivo;
    private Date data;
    private String observacao;
}
