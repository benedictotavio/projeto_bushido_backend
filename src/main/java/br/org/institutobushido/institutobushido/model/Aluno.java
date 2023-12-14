package br.org.institutobushido.institutobushido.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.org.institutobushido.enums.Imovel;
import br.org.institutobushido.enums.TransportType;
import br.org.institutobushido.enums.Turno;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "alunos")
public class Aluno {

    @Id
    private Long id;
    private String nome;
    private boolean bolsaFamilia;
    private boolean auxilioBrasil;
    private Imovel imovel;
    private int numerosDePessoasNaCasa;
    private int contribuintesDaRendaFamiliar;
    private boolean alunoContribuiParaRenda;
    /**
     * TODO
     * Quantidade de salarios minimos na renda familiar
     */
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
}
