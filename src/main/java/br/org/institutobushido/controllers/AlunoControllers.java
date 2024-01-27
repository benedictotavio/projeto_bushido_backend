package br.org.institutobushido.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.MongoException;

import br.org.institutobushido.dtos.aluno.AlunoDTORequest;
import br.org.institutobushido.dtos.aluno.AlunoDTOResponse;
import br.org.institutobushido.dtos.aluno.objects.responsavel.ResponsavelDTORequest;
import br.org.institutobushido.dtos.aluno.objects.responsavel.ResponsavelDTOResponse;
import br.org.institutobushido.model.aluno.objects.Faltas;
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
            return ResponseEntity.created(URI.create("localhost")).body(novoAluno.rg());
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

    @PatchMapping("adicionarFalta/{rg}")
    public ResponseEntity<String> adicionarFaltaAoAluno(@Valid @RequestBody Faltas faltas,
            @PathVariable String rg) {
        try {
            String res = alunoServices.adicionarFaltaDoAluno(rg, faltas);
            return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("retirarFalta/{rg}")
    public ResponseEntity<String> retirarFaltaAoAluno(@RequestParam(name = "id") int faltasId,
            @PathVariable String rg) {

        try {
            String res = alunoServices.retirarFaltaDoAluno(rg, faltasId);
            return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
