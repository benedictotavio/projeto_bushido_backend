package br.org.institutobushido.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import br.org.institutobushido.dtos.aluno.ResponsavelDTORequest;
import br.org.institutobushido.dtos.aluno.ResponsavelDTOResponse;
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
    ResponseEntity<String> adicionarAluno(@Valid @RequestBody AlunoDTORequest alunoDTORequest) {
        try {
            AlunoDTOResponse novoAluno = alunoServices.adicionarAluno(alunoDTORequest);
            return ResponseEntity.created(URI.create("localhost")).body(novoAluno.rg());
        } catch (MongoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("adicionarFalta/{rg}")
    ResponseEntity<String> adicionarFalta(@PathVariable String rg) {
        try {
            int faltas = alunoServices.adicionarFaltaDoAluno(rg);
            return ResponseEntity.ok().body(String.valueOf(faltas));
        } catch (MongoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("retirarFalta/{rg}")
    ResponseEntity<String> retirarFalta(@PathVariable String rg) {
        try {
            int faltas = alunoServices.retirarFaltaDoAluno(rg);
            return ResponseEntity.ok().body(String.valueOf(faltas));
        } catch (MongoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("adicionarResponsavel/{rg}")
    public ResponseEntity<Object> postMethodName(@PathVariable String rg,
            @RequestBody ResponsavelDTORequest responsavelDTORequest) {

        try {
            ResponsavelDTOResponse res = alunoServices.adicionarResponsavel(rg, responsavelDTORequest);
            return ResponseEntity.ok().body(res);
        } catch (MongoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
