package br.org.institutobushido.controllers;

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

import com.mongodb.MongoException;

import br.org.institutobushido.abstracts.InformacoesSaudeImpl;
import br.org.institutobushido.dtos.aluno.AlunoDTORequest;
import br.org.institutobushido.dtos.aluno.AlunoDTOResponse;
import br.org.institutobushido.dtos.aluno.objects.graduacao.faltas.FaltaDTORequest;
import br.org.institutobushido.dtos.aluno.objects.responsavel.ResponsavelDTORequest;
import br.org.institutobushido.dtos.aluno.objects.responsavel.ResponsavelDTOResponse;
import br.org.institutobushido.services.aluno.AlunoServices;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/V1/aluno")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AlunoControllers {

    @Autowired
    private AlunoServices alunoServices;

    @GetMapping()
    ResponseEntity<Object> buscarAlunoPorEmail(@RequestParam(name = "rg") String rg) {
        try {
            AlunoDTOResponse alunoEncontrado = alunoServices.buscarAlunoPorRg(rg);
            return ResponseEntity.ok().body(alunoEncontrado);
        } catch (MongoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping()
    ResponseEntity<String> adicionarAluno(@Valid() @RequestBody AlunoDTORequest alunoDTORequest) {
        try {
            AlunoDTOResponse novoAluno = alunoServices.adicionarAluno(alunoDTORequest);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
            return ResponseEntity.created(location).body(novoAluno.rg());
        } catch (MongoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("adicionarResponsavel/{rg}")
    public ResponseEntity<Object> adicionarResponsavel(@PathVariable String rg,
            @RequestBody ResponsavelDTORequest responsavelDTORequest) {
        try {
            ResponsavelDTOResponse res = alunoServices.adicionarResponsavel(rg, responsavelDTORequest);
            return ResponseEntity.ok().body(res);
        } catch (MongoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("removerResponsavel/{rg}")
    public ResponseEntity<Object> removerResponsavel(@PathVariable String rg,
            @RequestParam(name = "cpf") String cpf) {
        try {
            boolean res = alunoServices.removerResponsavel(rg, cpf);
            return ResponseEntity.ok().body(res);
        } catch (MongoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("adicionarFalta/{rg}")
    public ResponseEntity<String> adicionarFaltaAoAluno(@Valid @RequestBody FaltaDTORequest faltas,
            @PathVariable String rg) {
        try {
            String res = alunoServices.adicionarFaltaDoAluno(rg, faltas);
            return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("adicionarFalta/{rg}/{data}")
    public ResponseEntity<String> adicionarFaltaAoAluno(@Valid @RequestBody FaltaDTORequest faltas,
            @PathVariable String rg, @PathVariable long data) {
        try {
            String res = alunoServices.adicionarFaltaDoAluno(rg, faltas, data);
            return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("retirarFalta/{rg}")
    public ResponseEntity<String> retirarFaltaAoAluno(@RequestParam(name = "data") String data,
            @PathVariable String rg) {
        try {
            String res = alunoServices.retirarFaltaDoAluno(rg, data);
            return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("deficiencia/{rg}")
    public ResponseEntity<String> adicionarDeficiencia(@PathVariable String rg,
            @RequestParam(name = "deficiencia") String deficiencia) {
        try {
            String res = alunoServices.adicionarDeficiencia(rg, deficiencia);
            return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("deficiencia/{rg}")
    public ResponseEntity<String> removerDeficiencia(@PathVariable String rg,
            @RequestParam(name = "deficiencia") String deficiencia) {
        try {
            String res = alunoServices.removerDeficiencia(rg, deficiencia);
            return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("acompanhamentoSaude/{rg}")
    public ResponseEntity<String> adicionarAcompanhamentoSaude(@PathVariable String rg,
            @RequestParam(name = "acompanhamento") String acompanhamento) {
        try {
            String res = alunoServices.adicionarAcompanhamentoSaude(rg, acompanhamento);
            return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("acompanhamentoSaude/{rg}")
    public ResponseEntity<Object> removerAcompanhamentoSaude(@PathVariable String rg,
            @RequestParam(name = "acompanhamento") String acompanhamento) {
        try {
            Object res = alunoServices.removerAcompanhamentoSaude(rg, acompanhamento);
            return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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

        try {
            Object res = alunoServices.editarHistoricoDeSaude(rg, object.getKey(), object.getValue().getTipo(),
                    true);
            return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
