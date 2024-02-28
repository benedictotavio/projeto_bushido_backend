package br.org.institutobushido.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mongodb.MongoException;

import br.org.institutobushido.dtos.admin.login.LoginDTORequest;
import br.org.institutobushido.dtos.admin.login.LoginDTOResponse;
import br.org.institutobushido.dtos.admin.signup.SignUpDTORequest;
import br.org.institutobushido.dtos.admin.signup.SignUpDTOResponse;
import br.org.institutobushido.model.admin.Admin;
import br.org.institutobushido.services.admin.AdminServices;
import br.org.institutobushido.services.admin.infra.TokenServices;
import jakarta.validation.Valid;

@RestController(value = "admin")
@RequestMapping("api/V1/admin")
public class AdminControllers {
    @Autowired
    private AdminServices adminServices;

    @Autowired
    private TokenServices tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignUpDTORequest signUpDTORequest) {
        try {
            SignUpDTOResponse admin = this.adminServices.signup(signUpDTORequest);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
            return ResponseEntity.created(location).body(admin.email());
        } catch (MongoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTOResponse> login(@RequestBody @Valid LoginDTORequest loginDTORequest) {
        var login = new UsernamePasswordAuthenticationToken(loginDTORequest.email(), loginDTORequest.senha());
        Authentication auth = this.authenticationManager.authenticate(login);
        var token = this.tokenService.generateToken((Admin) auth.getPrincipal());
        return ResponseEntity.ok().body(new LoginDTOResponse(token));
    }
}
