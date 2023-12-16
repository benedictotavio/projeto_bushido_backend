package br.org.institutobushido.model;

import java.util.Date;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import br.org.institutobushido.enums.Imovel;
import br.org.institutobushido.enums.TransportType;
import br.org.institutobushido.enums.Turno;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "alunos")
public class Aluno {

    private String nome;
    private boolean bolsaFamilia;
    private boolean auxilioBrasil;
    private Imovel imovel;
    private int numerosDePessoasNaCasa;
    private int contribuintesDaRendaFamiliar;
    private boolean alunoContribuiParaRenda;
    private int rendaFamiliarEmSalariosMinimos;
    private TransportType transporte;
    private boolean vemAcompanhado;
    private Turno turno;
    private Date dataPreenchimento;
    private String cidade;
    private String estado;
    private String rg;
    private String cpfResponsavel;
    private int faltas;
    private boolean status;

    private void atribuirFaltasNoStatus(){
        // TODO
    }

}


