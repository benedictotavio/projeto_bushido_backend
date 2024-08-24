package br.org.institutobushido.controllers.routes.aluno;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.org.institutobushido.controllers.dtos.aluno.AlunoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.AlunoDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.UpdateAlunoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.GraduacaoDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.faltas.FaltaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.responsavel.ResponsavelDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.responsavel.ResponsavelDTOResponse;
import br.org.institutobushido.controllers.response.success.SuccessDeleteResponse;
import br.org.institutobushido.controllers.response.success.SuccessPostResponse;
import br.org.institutobushido.controllers.response.success.SuccessPutResponse;
import br.org.institutobushido.models.aluno.Aluno;
import br.org.institutobushido.models.aluno.graduacao.Graduacao;
import br.org.institutobushido.models.aluno.graduacao.falta.Falta;
import br.org.institutobushido.models.aluno.historico_de_saude.HistoricoSaude;
import br.org.institutobushido.models.aluno.responsaveis.Responsavel;
import br.org.institutobushido.services.aluno.AlunoServicesInterface;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

@RestController(value = "aluno")
@RequestMapping("api/V1/aluno")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AlunoController {
        private final AlunoServicesInterface alunoServices;

        private static final String URI_ALUNO = "/api/V1/aluno";

        public AlunoController(AlunoServicesInterface alunoServices) {
                this.alunoServices = alunoServices;
        }

        @GetMapping()
        ResponseEntity<List<AlunoDTOResponse>> buscarAluno(@RequestParam(name = "matricula", required = false) String matricula,
                        @RequestParam(name = "nome", required = false) String nome,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "nome") String sortBy,
                        @RequestParam(defaultValue = "asc") String sortOrder) {
                return ResponseEntity.ok().body(alunoServices.buscarAluno(nome, matricula, page, size, sortOrder, sortBy));
        }

        @PostMapping(value = "comImagem",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
        ResponseEntity<SuccessPostResponse> adicionarAlunoComImagem(
                        @Valid @RequestPart("alunoDTORequest") AlunoDTORequest alunoDTORequest,
                        @RequestPart(value = "imagemAluno", required = false) MultipartFile imagemAluno)
                        throws URISyntaxException, IOException {
                String alunoAdicionado = this.alunoServices.adicionarAlunoComImagem(alunoDTORequest, imagemAluno);
                return ResponseEntity.created(
                                new URI(URI_ALUNO))
                                .body(new SuccessPostResponse(alunoAdicionado, "Aluno adicionado com sucesso",
                                                Aluno.class.getSimpleName()));
        }

        @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
        ResponseEntity<SuccessPostResponse> adicionarAluno(
                @Valid @RequestPart("alunoDTORequest") AlunoDTORequest alunoDTORequest
                )
                throws URISyntaxException{
                String alunoAdicionado = this.alunoServices.adicionarAluno(alunoDTORequest);
                return ResponseEntity.created(
                                new URI(URI_ALUNO))
                        .body(new SuccessPostResponse(alunoAdicionado, "Aluno adicionado com sucesso",
                                Aluno.class.getSimpleName()));
        }

        @PutMapping(value = "{matricula}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
        public ResponseEntity<SuccessPutResponse> editarAluno(@PathVariable String matricula,
                        @RequestPart("aluno") UpdateAlunoDTORequest aluno
                       ) throws IOException {
                String alunoEditado = this.alunoServices.editarAlunoPorMatricula(matricula, aluno);
                return ResponseEntity.ok().body(
                                new SuccessPutResponse(matricula, alunoEditado, Aluno.class.getSimpleName()));
        }

        @PutMapping(value = "comImagem/{matricula}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
        public ResponseEntity<SuccessPutResponse> editarAlunoComImagem(@PathVariable String matricula,
                                                                        @RequestPart("aluno") UpdateAlunoDTORequest aluno,
                                                                       @RequestPart("imagemAluno") MultipartFile imagemAluno
        ) throws IOException {

                String alunoEditado = this.alunoServices.editarAlunoPorMatriculaComImagem(matricula, aluno, imagemAluno);
                return ResponseEntity.ok().body(
                        new SuccessPutResponse(matricula, alunoEditado, Aluno.class.getSimpleName()));
        }

        @PostMapping("responsavel/{matricula}")
        public ResponseEntity<SuccessPostResponse> adicionarResponsavel(@PathVariable String matricula,
                        @Valid @RequestBody ResponsavelDTORequest responsavelDTORequest) {
                ResponsavelDTOResponse responsavel = alunoServices.adicionarResponsavel(matricula, responsavelDTORequest);
                return ResponseEntity.ok().body(new SuccessPostResponse(responsavel.cpf(),
                                "Responsável adicionado com sucesso", Responsavel.class.getSimpleName()));
        }

        @DeleteMapping("responsavel/{matricula}")
        public ResponseEntity<SuccessDeleteResponse> removerResponsavel(@PathVariable String matricula,
                        @RequestParam(name = "cpf") String cpfResponsavel) {
                String res = alunoServices.removerResponsavel(matricula, cpfResponsavel);
                return ResponseEntity.ok().body(
                                new SuccessDeleteResponse(res, "Responsável removido com sucesso",
                                                Responsavel.class.getSimpleName()));
        }

        @PostMapping("falta/{matricula}/{data}")
        public ResponseEntity<SuccessPostResponse> adicionarFaltaAoAluno(@Valid @RequestBody FaltaDTORequest faltas,
                        @PathVariable String matricula, @PathVariable long data) {
                String res = alunoServices.adicionarFaltaDoAluno(matricula, faltas, data);
                return ResponseEntity.ok()
                                .body(new SuccessPostResponse(res, "Falta adicionada", Falta.class.getSimpleName()));
        }

        @DeleteMapping("falta/{matricula}/{data}")
        public ResponseEntity<SuccessDeleteResponse> retirarFaltaAoAluno(@PathVariable("data") String data,
                        @PathVariable String matricula) {
                String res = alunoServices.retirarFaltaDoAluno(matricula, data);
                return ResponseEntity.ok()
                                .body(new SuccessDeleteResponse(res, "Falta retirada com sucesso",
                                                Falta.class.getSimpleName()));
        }

        @PostMapping("deficiencia/{matricula}")
        public ResponseEntity<SuccessPostResponse> adicionarDeficiencia(@PathVariable String matricula,
                        @RequestParam(name = "deficiencia") String deficiencia) {
                String res = alunoServices.adicionarDeficiencia(matricula, deficiencia);
                return ResponseEntity.ok().body(new SuccessPostResponse(res, "Deficiência adicionada",
                                HistoricoSaude.class.getSimpleName()));
        }

        @DeleteMapping("deficiencia/{matricula}")
        public ResponseEntity<SuccessDeleteResponse> removerDeficiencia(@PathVariable String matricula,
                        @RequestParam(name = "deficiencia") String deficiencia) {
                String res = alunoServices.removerDeficiencia(matricula, deficiencia);
                return ResponseEntity.ok()
                                .body(new SuccessDeleteResponse(res,
                                                "Deficiência " + deficiencia + " foi removida com sucesso.",
                                                HistoricoSaude.class.getSimpleName()));
        }

        @PostMapping("acompanhamentoSaude/{matricula}")
        public ResponseEntity<SuccessPostResponse> adicionarAcompanhamentoSaude(@PathVariable String matricula,
                        @RequestParam(name = "acompanhamento") String acompanhamento) {
                String res = alunoServices.adicionarAcompanhamentoSaude(matricula, acompanhamento);
                return ResponseEntity.ok()
                                .body(new SuccessPostResponse(res,
                                                "Acompanhamento " + acompanhamento + " foi adicionado com sucesso.",
                                                HistoricoSaude.class.getSimpleName()));
        }

        @DeleteMapping("acompanhamentoSaude/{matricula}")
        public ResponseEntity<SuccessDeleteResponse> removerAcompanhamentoSaude(@PathVariable String matricula,
                        @RequestParam(name = "acompanhamento") String acompanhamento) {
                String res = alunoServices.removerAcompanhamentoSaude(matricula, acompanhamento);
                return ResponseEntity.ok().body(
                                new SuccessDeleteResponse(res,
                                                "Acamponhamento " + acompanhamento + " foi removido com sucesso."));
        }

        @PostMapping("graduacao/{matricula}/aprovar/{nota}")
        public ResponseEntity<SuccessPostResponse> aprovarAluno(@PathVariable String matricula, @PathVariable int nota) {
                GraduacaoDTOResponse res = alunoServices.aprovarAluno(matricula, nota);
                return ResponseEntity.ok()
                                .body(new SuccessPostResponse(String.valueOf(res.kyu()),
                                                "Graduação concluída com sucesso.", Graduacao.class.getSimpleName()));
        }

        @PostMapping("graduacao/{matricula}/reprovar/{nota}")
        public ResponseEntity<SuccessPostResponse> reprovarAluno(@PathVariable String matricula, @PathVariable int nota) {
                GraduacaoDTOResponse res = alunoServices.reprovarAluno(matricula, nota);
                return ResponseEntity.ok()
                                .body(new SuccessPostResponse(String.valueOf(res.kyu()),
                                                "Graduação concluída com sucesso.", Graduacao.class.getSimpleName()));
        }

        @PutMapping("graduacao/{matricula}/mudarStatus/{status}")
        public ResponseEntity<SuccessPostResponse> mudarStatusAluno(@PathVariable String matricula, @PathVariable boolean status) {
                GraduacaoDTOResponse res = alunoServices.mudarStatusGraduacaoAluno(matricula, status);
                return ResponseEntity.ok()
                        .body(new SuccessPostResponse(String.valueOf(res.status()),
                                "Status do aluno alterado com sucesso.", Graduacao.class.getSimpleName()));
        }
}
