package br.org.institutobushido.controllers.routes.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.institutobushido.controllers.dtos.admin.AdminDTOResponse;
import br.org.institutobushido.controllers.dtos.admin.login.LoginDTORequest;
import br.org.institutobushido.controllers.dtos.admin.login.LoginDTOResponse;
import br.org.institutobushido.controllers.dtos.admin.signup.SignUpDTORequest;
import br.org.institutobushido.models.admin.Admin;
import br.org.institutobushido.services.admin.AdminServiceInterface;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController(value = "admin")
@RequestMapping("api/V1/admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminControllers {

    private AdminServiceInterface adminServices;
    private AuthenticationManager authenticationManager;

    public AdminControllers(AdminServiceInterface adminServices, AuthenticationManager authenticationManager) {
        this.adminServices = adminServices;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("signup")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> signup(@Valid @RequestBody SignUpDTORequest signUpDTORequest) {
        this.adminServices.signup(signUpDTORequest);
        return ResponseEntity.ok().body("Admin criado com sucesso");
    }

    @PostMapping("login")
    public ResponseEntity<LoginDTOResponse> login(@Valid @RequestBody LoginDTORequest loginDTORequest) {
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginDTORequest.email(),
                loginDTORequest.senha());
        Authentication auth = this.authenticationManager.authenticate(login);
        LoginDTOResponse token = this.adminServices.login((Admin) auth.getPrincipal());
        return ResponseEntity.ok().body(token);
    }

    @GetMapping("users")
    public List<AdminDTOResponse> users(@RequestParam(name = "nome") String nome) {
        return this.adminServices.buscarAdminPorNome(nome);
    }
    
}
