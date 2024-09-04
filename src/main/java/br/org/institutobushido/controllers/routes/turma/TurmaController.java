package br.org.institutobushido.controllers.routes.turma;

import br.org.institutobushido.controllers.dtos.turma.DadosTurmaDTOResponse;
import br.org.institutobushido.controllers.dtos.turma.TurmaDTORequest;
import br.org.institutobushido.controllers.dtos.turma.TurmaDTOResponse;
import br.org.institutobushido.controllers.response.error.StandardError;
import br.org.institutobushido.controllers.response.success.SuccessDeleteResponse;
import br.org.institutobushido.controllers.response.success.SuccessPostResponse;
import br.org.institutobushido.models.turma.Turma;
import br.org.institutobushido.services.turma.TurmaServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController(value = "turma")
@RequestMapping("api/V1/turma")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TurmaController {

    private final TurmaServiceInterface turmaService;

    private static final String URI_TURMA = "/api/V1/turma";

    public TurmaController(TurmaServiceInterface turmaService) {
        this.turmaService = turmaService;
    }

    @Operation(summary = "Criar nova turma",
            responses = {
                    @ApiResponse(description = "Turma criada com sucesso", responseCode = "201",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessPostResponse.class))),
                    @ApiResponse(description = "Erro ao criar turma", responseCode = "400",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class)))
            })
    @PostMapping
    public ResponseEntity<SuccessPostResponse> criarNovaTurma(@Valid @RequestBody TurmaDTORequest turmaDTORequest)
            throws URISyntaxException {
        String res = this.turmaService.criarNovaTurma(turmaDTORequest);
        return ResponseEntity.created(
                        new URI(
                                URI_TURMA))
                .body(
                        new SuccessPostResponse(turmaDTORequest.nome(), res,
                                Turma.class.getSimpleName()));
    }

    @Operation(summary = "Deletar turma",
            responses = {
                    @ApiResponse(description = "Turma deletada com sucesso", responseCode = "204",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessDeleteResponse.class))),
                    @ApiResponse(description = "Erro ao deletar turma", responseCode = "400",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class)))
            })
    @DeleteMapping("{nomeTurma}/{emailAdmin}")
    public ResponseEntity<SuccessDeleteResponse> deletarTurma(@PathVariable String nomeTurma,
                                                              @PathVariable String emailAdmin) {
        String res = this.turmaService.deletarTurma(emailAdmin, nomeTurma);
        return ResponseEntity.ok()
                .body(new SuccessDeleteResponse(nomeTurma, res, Turma.class.getSimpleName()));
    }

    @Operation(summary = "Listar turmas",
            responses = {
                    @ApiResponse(description = "Lista de turmas retornada com sucesso", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TurmaDTOResponse.class))),
                    @ApiResponse(description = "Erro ao listar turmas", responseCode = "400",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class)))
            })
    @GetMapping
    public ResponseEntity<List<TurmaDTOResponse>> listarTurmas(@RequestParam(required = false, name = "dataInicial", defaultValue = "0") long dataInicial, @RequestParam(required = false, name = "dataFinal", defaultValue = "0") long dataFinal) {
        var turmas = this.turmaService.listarTurmas(dataInicial, dataFinal);
        return ResponseEntity.ok().body(turmas);
    }

    @Operation(summary = "Buscar turma por nome",
            responses = {
                    @ApiResponse(description = "Turma encontrada com sucesso", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TurmaDTOResponse.class))),
                    @ApiResponse(description = "Erro ao buscar turma", responseCode = "404",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class)))
            })
    @GetMapping("{nomeTurma}")
    public ResponseEntity<TurmaDTOResponse> buscarTurmaPorNome(@PathVariable String nomeTurma) {
        var turma = this.turmaService.buscarTurmaPorNome(nomeTurma);
        return ResponseEntity.ok().body(turma);
    }

    @Operation(summary = "Listar alunos por turma",
            responses = {
                    @ApiResponse(description = "Lista de alunos retornada com sucesso", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DadosTurmaDTOResponse.class))),
                    @ApiResponse(description = "Erro ao listar alunos da turma", responseCode = "404",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class)))
            })
    @GetMapping("{nomeTurma}/alunos")
    public DadosTurmaDTOResponse listarAlunoPorTurma(@PathVariable String nomeTurma) {
        return this.turmaService.listarAlunosDaTurma(nomeTurma);
    }

}
