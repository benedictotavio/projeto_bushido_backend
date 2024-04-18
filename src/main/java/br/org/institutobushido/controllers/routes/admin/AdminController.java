package br.org.institutobushido.controllers.routes.admin;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import br.org.institutobushido.controllers.response.success.SuccessPostResponse;
import org.springframework.http.ResponseEntity;
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
import br.org.institutobushido.controllers.response.success.SuccessLoginAuthenticated;
import br.org.institutobushido.models.admin.Admin;
import br.org.institutobushido.services.admin.AdminServiceInterface;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController(value = "admin")
@RequestMapping("api/V1/admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {

    private AdminServiceInterface adminServices;
    private AuthenticationManager authenticationManager;

    public AdminController(AdminServiceInterface adminServices, AuthenticationManager authenticationManager) {
        this.adminServices = adminServices;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("signup")
    public ResponseEntity<SuccessPostResponse> signup(@Valid @RequestBody SignUpDTORequest signUpDTORequest) throws URISyntaxException {
        this.adminServices.signup(signUpDTORequest);
        return ResponseEntity.created(
               new URI("/api/V1/admin/signup")
        ).body(
                new SuccessPostResponse(signUpDTORequest.email(), "Admin criado com sucesso.",
                        Admin.class.getSimpleName()));
    }

    @PostMapping("login")
    public ResponseEntity<SuccessLoginAuthenticated> login(@Valid @RequestBody LoginDTORequest loginDTORequest) {
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginDTORequest.email(),
                loginDTORequest.senha());
        Authentication auth = this.authenticationManager.authenticate(login);
        LoginDTOResponse admin = this.adminServices.login((Admin) auth.getPrincipal());
        return ResponseEntity.ok().body(
                new SuccessLoginAuthenticated(admin.token(), admin.role()));
    }

    @GetMapping("users")
    public List<AdminDTOResponse> users(@RequestParam(name = "nome") String nome) {
        return this.adminServices.buscarAdminPorNome(nome);
    }
}
