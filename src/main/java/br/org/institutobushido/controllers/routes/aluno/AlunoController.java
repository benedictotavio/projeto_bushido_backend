package br.org.institutobushido.controllers.routes.aluno;

import br.org.institutobushido.controllers.dtos.aluno.AlunoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.AlunoDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.UpdateAlunoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.GraduacaoDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.faltas.FaltaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.responsavel.ResponsavelDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.responsavel.ResponsavelDTOResponse;
import br.org.institutobushido.controllers.response.error.StandardError;
import br.org.institutobushido.controllers.response.success.SuccessDeleteResponse;
import br.org.institutobushido.controllers.response.success.SuccessPostResponse;
import br.org.institutobushido.controllers.response.success.SuccessPutResponse;
import br.org.institutobushido.models.aluno.Aluno;
import br.org.institutobushido.models.aluno.graduacao.Graduacao;
import br.org.institutobushido.models.aluno.graduacao.falta.Falta;
import br.org.institutobushido.models.aluno.historico_de_saude.HistoricoSaude;
import br.org.institutobushido.models.aluno.responsaveis.Responsavel;
import br.org.institutobushido.services.aluno.AlunoServicesInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController(value = "aluno")
@RequestMapping("api/V1/aluno")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AlunoController {
        private final AlunoServicesInterface alunoServices;

        private static final String URI_ALUNO = "/api/V1/aluno";

        public AlunoController(AlunoServicesInterface alunoServices) {
                this.alunoServices = alunoServices;
        }

        @Operation(summary = "Buscar alunos",
                responses = {
                        @ApiResponse(description = "Lista de alunos retornada com sucesso", responseCode = "200",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = AlunoDTOResponse.class))),
                        @ApiResponse(description = "Erro ao buscar alunos", responseCode = "400",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = StandardError.class)))
                })
        @GetMapping()
        ResponseEntity<List<AlunoDTOResponse>> buscarAluno(
                        @RequestParam(name = "matricula", required = false) String matricula,
                        @RequestParam(name = "nome", required = false) String nome,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "nome") String sortBy,
                        @RequestParam(defaultValue = "asc") String sortOrder) {
                return ResponseEntity.ok()
                                .body(alunoServices.buscarAluno(nome, matricula, page, size, sortOrder, sortBy));
        }

        @Operation(summary = "Adicionar aluno com imagem",
                responses = {
                        @ApiResponse(description = "Aluno adicionado com sucesso", responseCode = "201",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = SuccessPostResponse.class))),
                        @ApiResponse(description = "Erro ao adicionar aluno", responseCode = "400",
                                content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = StandardError.class)))
                })
        @PostMapping(value = "comImagem", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
        ResponseEntity<SuccessPostResponse> adicionarAlunoComImagem(
                        @Valid @RequestPart("alunoDTORequest") AlunoDTORequest alunoDTORequest,
                        @RequestPart(value = "imagemAluno", required = false) MultipartFile imagemAluno)
                        throws URISyntaxException, IOException {
                String alunoAdicionado = this.alunoServices.adicionarAlunoComImagem(alunoDTORequest, imagemAluno);
                return ResponseEntity.created(
                                new URI(URI_ALUNO))
                                .body(new SuccessPostResponse(alunoAdicionado, "Aluno adicionado com sucesso",
                                                HttpStatus.CREATED.value(),
                                                Aluno.class.getSimpleName()));
        }

        @Operation(summary = "Adicionar aluno",
                responses = {
                        @ApiResponse(description = "Aluno adicionado com sucesso", responseCode = "201",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = SuccessPostResponse.class))),
                        @ApiResponse(description = "Erro ao adicionar aluno", responseCode = "400",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = StandardError.class)))
                })
        @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
        ResponseEntity<SuccessPostResponse> adicionarAluno(
                        @Valid @RequestPart("alunoDTORequest") AlunoDTORequest alunoDTORequest)
                        throws URISyntaxException {
                String alunoAdicionado = this.alunoServices.adicionarAluno(alunoDTORequest);
                return ResponseEntity.created(
                                new URI(URI_ALUNO))
                                .body(new SuccessPostResponse(alunoAdicionado, "Aluno adicionado com sucesso",
                                                HttpStatus.CREATED.value(),
                                                Aluno.class.getSimpleName()));
        }

        @Operation(summary = "Editar aluno",
                responses = {
                        @ApiResponse(description = "Aluno editado com sucesso", responseCode = "200",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = SuccessPutResponse.class))),
                        @ApiResponse(description = "Erro ao editar aluno", responseCode = "400",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = StandardError.class)))
                })
        @PutMapping(value = "{matricula}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
        public ResponseEntity<SuccessPutResponse> editarAluno(@PathVariable String matricula,
                        @RequestPart("aluno") UpdateAlunoDTORequest aluno) throws IOException {
                String alunoEditado = this.alunoServices.editarAlunoPorMatricula(matricula, aluno);
                return ResponseEntity.ok().body(
                                new SuccessPutResponse(matricula, alunoEditado, Aluno.class.getSimpleName()));
        }

        @Operation(summary = "Editar aluno com imagem",
                responses = {
                        @ApiResponse(description = "Aluno editado com imagem com sucesso", responseCode = "200",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = SuccessPutResponse.class))),
                        @ApiResponse(description = "Erro ao editar aluno com imagem", responseCode = "400",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = StandardError.class)))
                })
        @PutMapping(value = "comImagem/{matricula}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
        public ResponseEntity<SuccessPutResponse> editarAlunoComImagem(@PathVariable String matricula,
                        @RequestPart("aluno") UpdateAlunoDTORequest aluno,
                        @RequestPart("imagemAluno") MultipartFile imagemAluno) throws IOException {

                String alunoEditado = this.alunoServices.editarAlunoPorMatriculaComImagem(matricula, aluno,
                                imagemAluno);
                return ResponseEntity.ok().body(
                                new SuccessPutResponse(matricula, alunoEditado, Aluno.class.getSimpleName()));
        }

        @Operation(summary = "Adicionar responsável ao aluno",
                responses = {
                        @ApiResponse(description = "Responsável adicionado com sucesso", responseCode = "200",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = SuccessPostResponse.class))),
                        @ApiResponse(description = "Erro ao adicionar responsável", responseCode = "400",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = StandardError.class)))
                })
        @PostMapping("responsavel/{matricula}")
        public ResponseEntity<SuccessPostResponse> adicionarResponsavel(@PathVariable String matricula,
                        @Valid @RequestBody ResponsavelDTORequest responsavelDTORequest) {
                ResponsavelDTOResponse responsavel = alunoServices.adicionarResponsavel(matricula,
                                responsavelDTORequest);
                return ResponseEntity.ok().body(new SuccessPostResponse(responsavel.cpf(),
                                "Responsável adicionado com sucesso", Responsavel.class.getSimpleName()));
        }

        @Operation(summary = "Remover responsável do aluno",
                responses = {
                        @ApiResponse(description = "Responsável removido com sucesso", responseCode = "204",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = SuccessDeleteResponse.class))),
                        @ApiResponse(description = "Erro ao remover responsável", responseCode = "400",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = StandardError.class)))
                })
        @DeleteMapping("responsavel/{matricula}")
        public ResponseEntity<SuccessDeleteResponse> removerResponsavel(@PathVariable String matricula,
                        @RequestParam(name = "cpf") String cpfResponsavel) {
                String res = alunoServices.removerResponsavel(matricula, cpfResponsavel);
                return ResponseEntity.ok().body(
                                new SuccessDeleteResponse(res, "Responsável removido com sucesso",
                                                Responsavel.class.getSimpleName()));
        }

        @Operation(summary = "Adicionar falta ao aluno",
                responses = {
                        @ApiResponse(description = "Falta adicionada com sucesso", responseCode = "200",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = SuccessPostResponse.class))),
                        @ApiResponse(description = "Erro ao adicionar falta", responseCode = "400",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = StandardError.class)))
                })
        @PostMapping("falta/{matricula}/{data}")
        public ResponseEntity<SuccessPostResponse> adicionarFaltaAoAluno(@Valid @RequestBody FaltaDTORequest faltas,
                        @PathVariable String matricula, @PathVariable long data) {
                String res = alunoServices.adicionarFaltaDoAluno(matricula, faltas, data);
                return ResponseEntity.ok()
                                .body(new SuccessPostResponse(res, "Falta adicionada", Falta.class.getSimpleName()));
        }

        @Operation(summary = "Retirar falta do aluno",
                responses = {
                        @ApiResponse(description = "Falta retirada com sucesso", responseCode = "204",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = SuccessDeleteResponse.class))),
                        @ApiResponse(description = "Erro ao retirar falta", responseCode = "400",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = StandardError.class)))
                })
        @DeleteMapping("falta/{matricula}/{data}")
        public ResponseEntity<SuccessDeleteResponse> retirarFaltaAoAluno(@PathVariable("data") String data,
                        @PathVariable String matricula) {
                String res = alunoServices.retirarFaltaDoAluno(matricula, data);
                return ResponseEntity.ok()
                                .body(new SuccessDeleteResponse(res, "Falta retirada com sucesso",
                                                Falta.class.getSimpleName()));
        }

        @Operation(summary = "Adicionar deficiência ao aluno",
                responses = {
                        @ApiResponse(description = "Deficiência adicionada com sucesso", responseCode = "200",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = SuccessPostResponse.class))),
                        @ApiResponse(description = "Erro ao adicionar deficiência", responseCode = "400",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = StandardError.class)))
                })
        @PostMapping("deficiencia/{matricula}")
        public ResponseEntity<SuccessPostResponse> adicionarDeficiencia(@PathVariable String matricula,
                        @RequestParam(name = "deficiencia") String deficiencia) {
                String res = alunoServices.adicionarDeficiencia(matricula, deficiencia);
                return ResponseEntity.ok().body(new SuccessPostResponse(res, "Deficiência adicionada",
                                HistoricoSaude.class.getSimpleName()));
        }

        @Operation(summary = "Remover deficiência do aluno",
                responses = {
                        @ApiResponse(description = "Deficiência removida com sucesso", responseCode = "204",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = SuccessDeleteResponse.class))),
                        @ApiResponse(description = "Erro ao remover deficiência", responseCode = "400",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = StandardError.class)))
                })
        @DeleteMapping("deficiencia/{matricula}")
        public ResponseEntity<SuccessDeleteResponse> removerDeficiencia(@PathVariable String matricula,
                        @RequestParam(name = "deficiencia") String deficiencia) {
                String res = alunoServices.removerDeficiencia(matricula, deficiencia);
                return ResponseEntity.ok()
                                .body(new SuccessDeleteResponse(res,
                                                "Deficiência " + deficiencia + " foi removida com sucesso.",
                                                HistoricoSaude.class.getSimpleName()));
        }

        @Operation(summary = "Adicionar acompanhamento de saúde ao aluno",
                responses = {
                        @ApiResponse(description = "Acompanhamento de saúde adicionado com sucesso", responseCode = "200",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = SuccessPostResponse.class))),
                        @ApiResponse(description = "Erro ao adicionar acompanhamento de saúde", responseCode = "400",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = StandardError.class)))
                })
        @PostMapping("acompanhamentoSaude/{matricula}")
        public ResponseEntity<SuccessPostResponse> adicionarAcompanhamentoSaude(@PathVariable String matricula,
                        @RequestParam(name = "acompanhamento") String acompanhamento) {
                String res = alunoServices.adicionarAcompanhamentoSaude(matricula, acompanhamento);
                return ResponseEntity.ok()
                                .body(new SuccessPostResponse(res,
                                                "Acompanhamento " + acompanhamento + " foi adicionado com sucesso.",
                                                HistoricoSaude.class.getSimpleName()));
        }

        @Operation(summary = "Remover acompanhamento de saúde do aluno",
                responses = {
                        @ApiResponse(description = "Acompanhamento de saúde removido com sucesso", responseCode = "204",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = SuccessDeleteResponse.class))),
                        @ApiResponse(description = "Erro ao remover acompanhamento de saúde", responseCode = "400",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = StandardError.class)))
                })
        @DeleteMapping("acompanhamentoSaude/{matricula}")
        public ResponseEntity<SuccessDeleteResponse> removerAcompanhamentoSaude(@PathVariable String matricula,
                        @RequestParam(name = "acompanhamento") String acompanhamento) {
                String res = alunoServices.removerAcompanhamentoSaude(matricula, acompanhamento);
                return ResponseEntity.ok().body(
                                new SuccessDeleteResponse(res,
                                                "Acamponhamento " + acompanhamento + " foi removido com sucesso."));
        }

        @Operation(summary = "Aprovar aluno",
                responses = {
                        @ApiResponse(description = "Aluno aprovado com sucesso", responseCode = "200",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = SuccessPostResponse.class))),
                        @ApiResponse(description = "Erro ao aprovar aluno", responseCode = "400",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = StandardError.class)))
                })
        @PostMapping("graduacao/{matricula}/aprovar/{nota}")
        public ResponseEntity<SuccessPostResponse> aprovarAluno(@PathVariable String matricula,
                        @PathVariable int nota) {
                GraduacaoDTOResponse res = alunoServices.aprovarAluno(matricula, nota);
                return ResponseEntity.ok()
                                .body(new SuccessPostResponse(String.valueOf(res.kyu()),
                                                "Graduação concluída com sucesso.", Graduacao.class.getSimpleName()));
        }


        @Operation(summary = "Reprovar aluno",
                responses = {
                        @ApiResponse(description = "Aluno reprovado com sucesso", responseCode = "200",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = SuccessPostResponse.class))),
                        @ApiResponse(description = "Erro ao reprovar aluno", responseCode = "400",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = StandardError.class)))
                })
        @PostMapping("graduacao/{matricula}/reprovar/{nota}")
        public ResponseEntity<SuccessPostResponse> reprovarAluno(@PathVariable String matricula,
                        @PathVariable int nota) {
                GraduacaoDTOResponse res = alunoServices.reprovarAluno(matricula, nota);
                return ResponseEntity.ok()
                                .body(new SuccessPostResponse(String.valueOf(res.kyu()),
                                                "Graduação concluída com sucesso.", Graduacao.class.getSimpleName()));
        }

        @Operation(summary = "Mudar status do aluno",
                responses = {
                        @ApiResponse(description = "Status do aluno alterado com sucesso", responseCode = "200",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = SuccessPostResponse.class))),
                        @ApiResponse(description = "Erro ao mudar status do aluno", responseCode = "400",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = StandardError.class)))
                })
        @PutMapping("graduacao/{matricula}/mudarStatus/{status}")
        public ResponseEntity<SuccessPostResponse> mudarStatusAluno(@PathVariable String matricula,
                        @PathVariable boolean status) {
                GraduacaoDTOResponse res = alunoServices.mudarStatusGraduacaoAluno(matricula, status);
                return ResponseEntity.ok()
                                .body(new SuccessPostResponse(String.valueOf(res.status()),
                                                "Status do aluno alterado com sucesso.",
                                                Graduacao.class.getSimpleName()));
        }
}
