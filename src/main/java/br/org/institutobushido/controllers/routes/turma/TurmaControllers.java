package br.org.institutobushido.controllers.routes.turma;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.org.institutobushido.controllers.dtos.turma.TurmaDTORequest;
import br.org.institutobushido.controllers.dtos.turma.TurmaDTOResponse;
import br.org.institutobushido.controllers.dtos.turma.aluno.AlunoDTORequest;
import br.org.institutobushido.controllers.response.success.SuccessDeleteResponse;
import br.org.institutobushido.controllers.response.success.SuccessPostResponse;
import br.org.institutobushido.model.turma.Turma;
import br.org.institutobushido.services.turma.TurmaServiceInterface;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;

@RestController(value = "turma")
@RequestMapping("api/V1/turma")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TurmaControllers {

    private final TurmaServiceInterface turmaService;

    public TurmaControllers(TurmaServiceInterface turmaService) {
        this.turmaService = turmaService;
    }

    @PostMapping
    public ResponseEntity<SuccessPostResponse> criarNovaTurma(@Valid() @RequestBody TurmaDTORequest turmaDTORequest) {
        this.turmaService.criarNovaTurma(turmaDTORequest);
        return ResponseEntity.ok().body(
                new SuccessPostResponse(turmaDTORequest.nome(), "Turma criada com sucesso",
                        Turma.class.getSimpleName()));
    }

    @DeleteMapping("{nomeTurma}")
    public ResponseEntity<SuccessDeleteResponse> deletarTurma(@PathVariable String nomeTurma) {
        this.turmaService.deletarTurma(nomeTurma);
        return ResponseEntity.ok()
                .body(new SuccessDeleteResponse(nomeTurma, "Turma removida com sucesso", Turma.class.getSimpleName()));
    }

    @PostMapping("/aluno/{nomeTurma}")
    public ResponseEntity<SuccessPostResponse> adicionarAluno(@PathVariable String nomeTurma,
            @Valid @RequestBody AlunoDTORequest aluno) {
        this.turmaService.adicionarAlunoATurma(nomeTurma, aluno);
        return ResponseEntity.ok()
                .body(new SuccessPostResponse(aluno.rg(), "Aluno adicionado com sucesso", Turma.class.getSimpleName()));
    }

    @GetMapping
    public ResponseEntity<List<TurmaDTOResponse>> listarTurmas() {
        var turmas = this.turmaService.listarTurmas();
        return ResponseEntity.ok().body(turmas);
    }

    @GetMapping("{nomeTurma}")
    public ResponseEntity<TurmaDTOResponse> buscarTurmaPorNome(@PathVariable String nomeTurma) {
        var turma = this.turmaService.buscarTurmaPorNome(nomeTurma);
        return ResponseEntity.ok().body(turma);
    }

}
