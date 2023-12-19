package br.org.institutobushido.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.institutobushido.dtos.aluno.AlunoDTORequest;
import br.org.institutobushido.services.aluno.AlunoServices;

@RestController
@RequestMapping("api/V1/aluno")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AlunoControllers {

    @Autowired
    private AlunoServices alunoServices;

    @PostMapping()
    ResponseEntity<?> adicionarAluno(@RequestBody AlunoDTORequest alunoDTORequest) {
        try {
            AlunoDTORequest novoAluno = alunoServices.adicionarAluno(alunoDTORequest);
            // Alterar URI
            return ResponseEntity.created(URI.create("localhost")).body(novoAluno);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e);
        }
    }
}
