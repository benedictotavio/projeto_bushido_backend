package br.org.institutobushido.dtos.aluno;

import java.util.Date;

import br.org.institutobushido.enums.Imovel;
import br.org.institutobushido.enums.TransportType;
import br.org.institutobushido.enums.Turno;


public record AlunoDTORequest(
        String nome,
        boolean bolsaFamilia,
        boolean auxilioBrasil,
        Imovel imovel,
        int numerosDePessoasNaCasa,
        int contribuintesDaRendaFamiliar,
        boolean alunoContribuiParaRenda,
        int rendaFamiliarEmSalariosMinimos,
        TransportType transporte,
        boolean vemAcompanhado,
        Turno turno,
        Date dataPreenchimento,
        String cidade,
        String estado,
        String rg,
        String cpfResponsavel,
        int faltas,
        boolean status
) {

    public boolean auxilioBrasil() {
        return auxilioBrasil;
    }

    public String nome() {
        return nome;
    }

    public boolean bolsaFamilia() {
        return bolsaFamilia;
    }

    public Imovel imovel() {
        return imovel;
    }

    public int numerosDePessoasNaCasa() {
        return numerosDePessoasNaCasa;
    }

    public int contribuintesDaRendaFamiliar() {
        return contribuintesDaRendaFamiliar;
    }

    public boolean alunoContribuiParaRenda() {
        return alunoContribuiParaRenda;
    }

    public int rendaFamiliarEmSalariosMinimos() {
        return rendaFamiliarEmSalariosMinimos;
    }

    public TransportType transporte() {
        return transporte;
    }

    public boolean vemAcompanhado() {
        return vemAcompanhado;
    }

    public Turno turno() {
        return turno;
    }

    public Date dataPreenchimento() {
        return dataPreenchimento;
    }

    public String cidade() {
        return cidade;
    }

    public String estado() {
        return estado;
    }

    public String rg() {
        return rg;
    }

    public String cpfResponsavel() {
        return cpfResponsavel;
    }

    public int faltas() {
        return faltas;
    }

    public boolean status() {
        return status;
    }
  
    
}