package br.org.institutobushido.dtos.aluno;

import java.util.Date;
import java.util.List;

import br.org.institutobushido.enums.Imovel;
import br.org.institutobushido.enums.TipoDeTransporte;
import br.org.institutobushido.enums.Turno;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class AlunoDTOResponse {

    private String nome;
    private boolean bolsaFamilia;
    private boolean auxilioBrasil;
    private Imovel imovel;
    private int numerosDePessoasNaCasa;
    private int contribuintesDaRendaFamiliar;
    private boolean alunoContribuiParaRenda;
    private int rendaFamiliarEmSalariosMinimos;
    private TipoDeTransporte transporte;
    private boolean vemAcompanhado;
    private Turno turno;
    private Date dataPreenchimento;
    private String cidade;
    private String estado;
    private String rg;
    private List<ResponsavelDTOResponse> responsaveis;
    private int faltas;
    private boolean status;
    
}
