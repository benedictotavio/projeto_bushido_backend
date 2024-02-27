package br.org.institutobushido.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mongodb.MongoException;

import br.org.institutobushido.dtos.admin.AdminDTORequest;
import br.org.institutobushido.dtos.admin.AdminDTOResponse;
import br.org.institutobushido.services.admin.AdminServices;
import jakarta.validation.Valid;

@RestController(value = "admin")
@RequestMapping("api/V1/admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminControllers {
    @Autowired
    private AdminServices adminServices;

    @PostMapping()
    public ResponseEntity<String> signup(@Valid @RequestBody AdminDTORequest adminDTORequest) {

        try {
            AdminDTOResponse admin = adminServices.signup(adminDTORequest);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
            return ResponseEntity.created(location).body(admin.email());
        } catch (MongoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
