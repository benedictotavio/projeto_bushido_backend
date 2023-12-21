package br.org.institutobushido.services.aluno;

import br.org.institutobushido.dtos.aluno.AlunoDTORequest;
import br.org.institutobushido.dtos.aluno.AlunoDTOResponse;

public interface AlunoServicesInterface {
    AlunoDTOResponse adicionarAluno(AlunoDTORequest alunoDTORequest);

    AlunoDTOResponse buscarAlunoPorRg(String rg);
}
