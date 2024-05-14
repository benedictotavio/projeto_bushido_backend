package br.org.institutobushido.services.aluno;

import java.io.IOException;
import java.util.List;

import br.org.institutobushido.controllers.dtos.aluno.AlunoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.AlunoDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.UpdateAlunoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.GraduacaoDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.faltas.FaltaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.responsavel.ResponsavelDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.responsavel.ResponsavelDTOResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AlunoServicesInterface {
    String adicionarAluno(AlunoDTORequest alunoDTORequest, MultipartFile imagemAluno) throws IOException;

    List<AlunoDTOResponse> buscarAluno(String nome, String cpf, int pagina, int tamanho, String ordenarPor,
            String sequenciaOrdenacao);

    public String editarAlunoPorCpf(String cpf, UpdateAlunoDTORequest updateAlunoDTORequest);

    public String adicionarFaltaDoAluno(String cpf, FaltaDTORequest faltas, long novaFalta);

    public String retirarFaltaDoAluno(String cpf, String faltasId);

    public ResponsavelDTOResponse adicionarResponsavel(String cpf, ResponsavelDTORequest responsavelDTORequest);

    public String removerResponsavel(String cpf, String cpfResponsavel);

    public String adicionarDeficiencia(String cpf, String deficiencia);

    public String removerDeficiencia(String cpf, String deficiencia);

    public String adicionarAcompanhamentoSaude(String cpf, String acompanhamentoSaude);

    public String removerAcompanhamentoSaude(String cpf, String acompanhamentoSaude);

    public GraduacaoDTOResponse aprovarAluno(String cpf, int nota);

    public GraduacaoDTOResponse reprovarAluno(String cpf, int nota);
}