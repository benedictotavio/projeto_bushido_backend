package br.org.institutobushido.services.aluno;

import br.org.institutobushido.dtos.aluno.AlunoDTORequest;
import br.org.institutobushido.dtos.aluno.AlunoDTOResponse;
import br.org.institutobushido.dtos.aluno.graduacao.faltas.FaltaDTORequest;
import br.org.institutobushido.dtos.aluno.responsavel.ResponsavelDTORequest;
import br.org.institutobushido.dtos.aluno.responsavel.ResponsavelDTOResponse;

public interface AlunoServicesInterface {
    AlunoDTOResponse adicionarAluno(AlunoDTORequest alunoDTORequest);

    AlunoDTOResponse buscarAlunoPorRg(String rg);

    public String adicionarFaltaDoAluno(String rg, FaltaDTORequest faltas);

    public String adicionarFaltaDoAluno(String rg, FaltaDTORequest faltas, long novaFalta);

    public String retirarFaltaDoAluno(String rg, String faltasId);

    public ResponsavelDTOResponse adicionarResponsavel(String rg, ResponsavelDTORequest responsavelDTORequest);

    public String removerResponsavel(String rg, String cpf);

    public String adicionarDeficiencia(String rg, String deficiencia);

    public String removerDeficiencia(String rg, String deficiencia);

    public String adicionarAcompanhamentoSaude(String rg, String acompanhamentoSaude);

    public String removerAcompanhamentoSaude(String rg, String acompanhamentoSaude);

    public Object editarHistoricoDeSaude(String rg, String campo, String historicoDeSaude, boolean resposta);
}
