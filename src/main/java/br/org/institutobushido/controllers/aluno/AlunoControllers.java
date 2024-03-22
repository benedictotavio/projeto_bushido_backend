package br.org.institutobushido.controllers.aluno;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.org.institutobushido.abstracts.InformacoesSaudeImpl;
import br.org.institutobushido.controllers.dtos.aluno.AlunoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.AlunoDTOResponse;
import br.org.institutobushido.controllers.dtos.aluno.UpdateAlunoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.faltas.FaltaDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.responsavel.ResponsavelDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.responsavel.ResponsavelDTOResponse;
import br.org.institutobushido.controllers.response.error.StandardError;
import br.org.institutobushido.controllers.response.success.SuccessDeleteResponse;
import br.org.institutobushido.controllers.response.success.SuccessPostResponse;
import br.org.institutobushido.controllers.response.success.SuccessPutResponse;
import br.org.institutobushido.model.aluno.Aluno;
import br.org.institutobushido.model.aluno.graduacao.falta.Falta;
import br.org.institutobushido.model.aluno.responsaveis.Responsavel;
import br.org.institutobushido.services.aluno.AlunoServicesInterface;
import jakarta.validation.Valid;

@RestController(value = "aluno")
@RequestMapping("api/V1/aluno")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AlunoControllers {

        private final AlunoServicesInterface alunoServices;

        public AlunoControllers(AlunoServicesInterface alunoServices) {
                this.alunoServices = alunoServices;
        }

        @GetMapping()
        ResponseEntity<AlunoDTOResponse> buscarAlunoPorRg(@RequestParam(name = "rg") String rg) {
                AlunoDTOResponse alunoEncontrado = alunoServices.buscarAluno(rg);
                return ResponseEntity.ok().body(alunoEncontrado);
        }

        @PostMapping()
        ResponseEntity<SuccessPostResponse> adicionarAluno(@Valid() @RequestBody AlunoDTORequest alunoDTORequest) {
                AlunoDTOResponse novoAluno = alunoServices.adicionarAluno(alunoDTORequest);
                URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
                return ResponseEntity.created(location)
                                .body(new SuccessPostResponse(novoAluno.rg(), "Aluno adicionado com sucesso",
                                                Aluno.class.getSimpleName()));
        }

        @PutMapping("{id}")
        public ResponseEntity<SuccessPutResponse> editarAluno(@PathVariable String id,
                        @RequestBody UpdateAlunoDTORequest aluno) {
                String alunoEditado = this.alunoServices.editarAlunoPorRg(id, aluno);
                return ResponseEntity.ok().body(
                                new SuccessPutResponse(id, alunoEditado, Aluno.class.getSimpleName()));
        }

        @PostMapping("adicionarResponsavel/{rg}")
        public ResponseEntity<SuccessPostResponse> adicionarResponsavel(@PathVariable String rg,
                        @Valid @RequestBody ResponsavelDTORequest responsavelDTORequest) {
                ResponsavelDTOResponse responsavel = alunoServices.adicionarResponsavel(rg, responsavelDTORequest);
                return ResponseEntity.ok().body(new SuccessPostResponse(responsavel.cpf(),
                                "Responsável adicionado com sucesso", Responsavel.class.getSimpleName()));
        }

        @DeleteMapping("removerResponsavel/{rg}")
        public ResponseEntity<SuccessDeleteResponse> removerResponsavel(@PathVariable String rg,
                        @RequestParam(name = "cpf") String cpf) {
                String res = alunoServices.removerResponsavel(rg, cpf);
                return ResponseEntity.ok().body(
                                new SuccessDeleteResponse(res, "Responsável removido com sucesso",
                                                Responsavel.class.getSimpleName()));
        }

        @PostMapping("adicionarFalta/{rg}")
        public ResponseEntity<SuccessPostResponse> adicionarFaltaAoAluno(@Valid @RequestBody FaltaDTORequest faltas,
                        @PathVariable String rg) {
                String res = alunoServices.adicionarFaltaDoAluno(rg, faltas);
                return ResponseEntity.ok()
                                .body(new SuccessPostResponse(res, "Falta adicionada", Falta.class.getSimpleName()));
        }

        @PostMapping("adicionarFalta/{rg}/{data}")
        public ResponseEntity<SuccessPostResponse> adicionarFaltaAoAluno(@Valid @RequestBody FaltaDTORequest faltas,
                        @PathVariable String rg, @PathVariable long data) {
                String res = alunoServices.adicionarFaltaDoAluno(rg, faltas, data);
                return ResponseEntity.ok()
                                .body(new SuccessPostResponse(res, "Falta adicionada", Falta.class.getSimpleName()));
        }

        @DeleteMapping("retirarFalta/{rg}")
        public ResponseEntity<SuccessDeleteResponse> retirarFaltaAoAluno(@RequestParam(name = "data") String data,
                        @PathVariable String rg) {
                String res = alunoServices.retirarFaltaDoAluno(rg, data);
                return ResponseEntity.ok()
                                .body(new SuccessDeleteResponse(res, "Falta retirada com sucesso",
                                                Falta.class.getSimpleName()));
        }

        @PostMapping("deficiencia/{rg}")
        public ResponseEntity<SuccessPostResponse> adicionarDeficiencia(@PathVariable String rg,
                        @RequestParam(name = "deficiencia") String deficiencia) {
                String res = alunoServices.adicionarDeficiencia(rg, deficiencia);
                return ResponseEntity.ok().body(new SuccessPostResponse(res, "Deficiência adicionada"));
        }

        @DeleteMapping("deficiencia/{rg}")
        public ResponseEntity<SuccessDeleteResponse> removerDeficiencia(@PathVariable String rg,
                        @RequestParam(name = "deficiencia") String deficiencia) {
                String res = alunoServices.removerDeficiencia(rg, deficiencia);
                return ResponseEntity.ok()
                                .body(new SuccessDeleteResponse(res,
                                                "Deficiência " + deficiencia + " foi removida com sucesso."));
        }

        @PostMapping("acompanhamentoSaude/{rg}")
        public ResponseEntity<SuccessPostResponse> adicionarAcompanhamentoSaude(@PathVariable String rg,
                        @RequestParam(name = "acompanhamento") String acompanhamento) {
                String res = alunoServices.adicionarAcompanhamentoSaude(rg, acompanhamento);
                return ResponseEntity.ok()
                                .body(new SuccessPostResponse(res,
                                                "Acompanhamento " + acompanhamento + " foi adicionado com sucesso."));
        }

        @DeleteMapping("acompanhamentoSaude/{rg}")
        public ResponseEntity<SuccessDeleteResponse> removerAcompanhamentoSaude(@PathVariable String rg,
                        @RequestParam(name = "acompanhamento") String acompanhamento) {
                String res = alunoServices.removerAcompanhamentoSaude(rg, acompanhamento);
                return ResponseEntity.ok().body(
                                new SuccessDeleteResponse(res,
                                                "Acamponhamento " + acompanhamento + " foi removido com sucesso."));
        }

        @PutMapping("historicoSaude/{rg}")
        public ResponseEntity<Object> adicionarHistoricoDeSaude(@PathVariable String rg,
                        @RequestBody Map.Entry<String, InformacoesSaudeImpl> object) {

                URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

                List<String> listaHistoricoSaude = List.of("alergia", "cirurgia", "usoMedicamentoContinuo",
                                "doencaCronica");

                if (!listaHistoricoSaude.contains(object.getKey())) {
                        return ResponseEntity.badRequest().body(new StandardError(Instant.now(), 400, "Bad Request",
                                        "Propriedade " + object.getKey()
                                                        + " inexistente. Siga as propriedades permitidas: "
                                                        + listaHistoricoSaude.toString(),
                                        location.getPath()));
                }

                if (!object.getValue().getResposta()) {
                        Object res = alunoServices.editarHistoricoDeSaude(rg, object.getKey(), "", false);
                        return ResponseEntity.ok().body(res);
                }

                if (object.getValue().getTipo().equals("")) {
                        Object res = alunoServices.editarHistoricoDeSaude(rg, object.getKey(), "",
                                        false);
                        return ResponseEntity.ok().body(res);
                }

                Object res = alunoServices.editarHistoricoDeSaude(rg, object.getKey(),
                                object.getValue().getTipo(),
                                true);
                return ResponseEntity.ok().body(res);
        }
}
