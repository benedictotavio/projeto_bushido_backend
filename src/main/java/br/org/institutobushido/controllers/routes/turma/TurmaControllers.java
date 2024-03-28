package br.org.institutobushido.controllers.routes.turma;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.institutobushido.controllers.dtos.turma.TurmaDTORequest;
import br.org.institutobushido.controllers.response.success.SuccessPostResponse;
import br.org.institutobushido.services.turma.TurmaServiceInterface;
import jakarta.validation.Valid;

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
        return ResponseEntity.ok().body(new SuccessPostResponse("nova turma criada", "Turmas encontradas", "Turma"));
    }
}
