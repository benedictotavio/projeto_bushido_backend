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

    String adicionarAlunoComImagem(AlunoDTORequest alunoDTORequest, MultipartFile imagemAluno) throws IOException;

    String adicionarAluno(AlunoDTORequest alunoDTORequest);

    List<AlunoDTOResponse> buscarAluno(String nome, String matricula, int pagina, int tamanho, String ordenarPor,
            String sequenciaOrdenacao);

    String editarAlunoPorMatricula(String matricula, UpdateAlunoDTORequest updateAlunoDTORequest) throws IOException;

    String editarAlunoPorMatriculaComImagem(String matricula, UpdateAlunoDTORequest updateAlunoDTORequest, MultipartFile imagemAluno) throws IOException;

    String adicionarFaltaDoAluno(String matricula, FaltaDTORequest faltas, long novaFalta);

    String retirarFaltaDoAluno(String matricula, String faltasId);

    ResponsavelDTOResponse adicionarResponsavel(String matricula, ResponsavelDTORequest responsavelDTORequest);

    String removerResponsavel(String matricula, String cpfResponsavel);

    String adicionarDeficiencia(String matricula, String deficiencia);

    String removerDeficiencia(String matricula, String deficiencia);

    String adicionarAcompanhamentoSaude(String matricula, String acompanhamentoSaude);

    String removerAcompanhamentoSaude(String matricula, String acompanhamentoSaude);

    GraduacaoDTOResponse aprovarAluno(String matricula, int nota);

    GraduacaoDTOResponse reprovarAluno(String matricula, int nota);

    GraduacaoDTOResponse mudarStatusGraduacaoAluno(String matricula, boolean nota);
}