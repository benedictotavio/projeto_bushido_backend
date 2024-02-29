package br.org.institutobushido.controllers.aluno;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.org.institutobushido.dtos.aluno.AlunoDTORequest;
import br.org.institutobushido.dtos.aluno.AlunoDTOResponse;
import br.org.institutobushido.dtos.aluno.graduacao.faltas.FaltaDTORequest;
import br.org.institutobushido.dtos.aluno.responsavel.ResponsavelDTORequest;
import br.org.institutobushido.dtos.aluno.responsavel.ResponsavelDTOResponse;
import br.org.institutobushido.services.aluno.AlunoServices;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/V1/aluno")
@CrossOrigin(origins = "https://institutobushido.netlify.app", allowedHeaders = { "Authorization", "Content-Type" })
public class AlunoControllers {

    @Autowired
    private AlunoServices alunoServices;

    @GetMapping()
    ResponseEntity<Object> buscarAlunoPorRg(@RequestParam(name = "rg") String rg) {
        AlunoDTOResponse alunoEncontrado = alunoServices.buscarAlunoPorRg(rg);
        return ResponseEntity.ok().body(alunoEncontrado);
    }

    @PostMapping()
    ResponseEntity<String> adicionarAluno(@Valid() @RequestBody AlunoDTORequest alunoDTORequest) {
        AlunoDTOResponse novoAluno = alunoServices.adicionarAluno(alunoDTORequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).body(novoAluno.rg());
    }

    @PostMapping("adicionarResponsavel/{rg}")
    public ResponseEntity<Object> adicionarResponsavel(@PathVariable String rg,
            @RequestBody ResponsavelDTORequest responsavelDTORequest) {
        ResponsavelDTOResponse res = alunoServices.adicionarResponsavel(rg, responsavelDTORequest);
        return ResponseEntity.ok().body(res);
    }

    @DeleteMapping("removerResponsavel/{rg}")
    public ResponseEntity<Object> removerResponsavel(@PathVariable String rg,
            @RequestParam(name = "cpf") String cpf) {
        boolean res = alunoServices.removerResponsavel(rg, cpf);
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("adicionarFalta/{rg}")
    public ResponseEntity<String> adicionarFaltaAoAluno(@Valid @RequestBody FaltaDTORequest faltas,
            @PathVariable String rg) {
        String res = alunoServices.adicionarFaltaDoAluno(rg, faltas);
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("adicionarFalta/{rg}/{data}")
    public ResponseEntity<String> adicionarFaltaAoAluno(@Valid @RequestBody FaltaDTORequest faltas,
            @PathVariable String rg, @PathVariable long data) {
        String res = alunoServices.adicionarFaltaDoAluno(rg, faltas, data);
        return ResponseEntity.ok().body(res);
    }

    @DeleteMapping("retirarFalta/{rg}")
    public ResponseEntity<String> retirarFaltaAoAluno(@RequestParam(name = "data") String data,
            @PathVariable String rg) {
        String res = alunoServices.retirarFaltaDoAluno(rg, data);
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("deficiencia/{rg}")
    public ResponseEntity<String> adicionarDeficiencia(@PathVariable String rg,
            @RequestParam(name = "deficiencia") String deficiencia) {
        String res = alunoServices.adicionarDeficiencia(rg, deficiencia);
        return ResponseEntity.ok().body(res);
    }

    @DeleteMapping("deficiencia/{rg}")
    public ResponseEntity<String> removerDeficiencia(@PathVariable String rg,
            @RequestParam(name = "deficiencia") String deficiencia) {
        String res = alunoServices.removerDeficiencia(rg, deficiencia);
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("acompanhamentoSaude/{rg}")
    public ResponseEntity<String> adicionarAcompanhamentoSaude(@PathVariable String rg,
            @RequestParam(name = "acompanhamento") String acompanhamento) {
        String res = alunoServices.adicionarAcompanhamentoSaude(rg, acompanhamento);
        return ResponseEntity.ok().body(res);
    }

    @DeleteMapping("acompanhamentoSaude/{rg}")
    public ResponseEntity<Object> removerAcompanhamentoSaude(@PathVariable String rg,
            @RequestParam(name = "acompanhamento") String acompanhamento) {
        Object res = alunoServices.removerAcompanhamentoSaude(rg, acompanhamento);
        return ResponseEntity.ok().body(res);
    }

    @PutMapping("historicoSaude/{rg}")
    public ResponseEntity<Object> adicionarHistoricoDeSaude(@PathVariable String rg,
            @RequestBody Map.Entry<String, InformacoesSaudeImpl> object) {

        if (!object.getValue().getResposta()) {
            try {
                Object res = alunoServices.editarHistoricoDeSaude(rg, object.getKey(), "", false);
                return ResponseEntity.ok().body(res);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        if (object.getValue().getTipo().equals("")) {
            try {
                Object res = alunoServices.editarHistoricoDeSaude(rg, object.getKey(), "",
                        false);
                return ResponseEntity.ok().body(res);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        try {
            Object res = alunoServices.editarHistoricoDeSaude(rg, object.getKey(), object.getValue().getTipo(),
                    true);
            return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
