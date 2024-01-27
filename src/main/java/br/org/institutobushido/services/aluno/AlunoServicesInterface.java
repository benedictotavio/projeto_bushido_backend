package br.org.institutobushido.services.aluno;

import br.org.institutobushido.dtos.aluno.AlunoDTORequest;
import br.org.institutobushido.dtos.aluno.AlunoDTOResponse;
import br.org.institutobushido.dtos.aluno.objects.responsavel.ResponsavelDTORequest;
import br.org.institutobushido.dtos.aluno.objects.responsavel.ResponsavelDTOResponse;
import br.org.institutobushido.model.aluno.objects.Faltas;

public interface AlunoServicesInterface {
    AlunoDTOResponse adicionarAluno(AlunoDTORequest alunoDTORequest);

    AlunoDTOResponse buscarAlunoPorRg(String rg);

    public String adicionarFaltaDoAluno(String rg, Faltas faltas);

    public String retirarFaltaDoAluno(String rg, String faltasId);

    public ResponsavelDTOResponse adicionarResponsavel(String rg, ResponsavelDTORequest responsavelDTORequest);

    public boolean removerResponsavel(String rg, String cpf);
}
