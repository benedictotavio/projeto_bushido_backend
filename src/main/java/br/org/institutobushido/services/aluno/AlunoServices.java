package br.org.institutobushido.services.aluno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.institutobushido.dtos.aluno.AlunoDTORequest;
import br.org.institutobushido.model.aluno.Aluno;
import br.org.institutobushido.repositories.AlunoRepositorio;

@Service
public class AlunoServices implements AlunoServicesInterface {
    
    @Autowired
    private AlunoRepositorio alunoRepositorio;

    public AlunoDTORequest adicionarAluno(AlunoDTORequest alunoDTORequest) {
        Aluno aluno = new Aluno();
        aluno.setNome(alunoDTORequest.nome());
        aluno.setBolsaFamilia(alunoDTORequest.bolsaFamilia());
        aluno.setImovel(alunoDTORequest.imovel());
        aluno.setAuxilioBrasil(alunoDTORequest.auxilioBrasil());
        aluno.setNumerosDePessoasNaCasa(alunoDTORequest.numerosDePessoasNaCasa());
        aluno.setCidade(alunoDTORequest.cidade());
        aluno.setCpfResponsavel(alunoDTORequest.cpfResponsavel());
        aluno.setDataPreenchimento(alunoDTORequest.dataPreenchimento());
        aluno.setContribuintesDaRendaFamiliar(alunoDTORequest.contribuintesDaRendaFamiliar());
        aluno.setEstado(alunoDTORequest.estado());
        aluno.setAlunoContribuiParaRenda(alunoDTORequest.alunoContribuiParaRenda());
        aluno.setRendaFamiliarEmSalariosMinimos(alunoDTORequest.rendaFamiliarEmSalariosMinimos());
        aluno.setTransporte(alunoDTORequest.transporte());
        aluno.setVemAcompanhado(alunoDTORequest.vemAcompanhado());
        aluno.setTurno(alunoDTORequest.turno());
        aluno.setRg(alunoDTORequest.rg());
        aluno.setFaltas(alunoDTORequest.faltas());
        aluno.setStatus(alunoDTORequest.status());

       alunoRepositorio.save(aluno);

        return alunoDTORequest;
    }
}
