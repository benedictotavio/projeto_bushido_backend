package br.org.institutobushido.controllers.admin;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.org.institutobushido.dtos.admin.login.LoginDTORequest;
import br.org.institutobushido.dtos.admin.login.LoginDTOResponse;
import br.org.institutobushido.dtos.admin.signup.SignUpDTORequest;
import br.org.institutobushido.dtos.admin.signup.SignUpDTOResponse;
import br.org.institutobushido.model.admin.Admin;
import br.org.institutobushido.resources.response.success.SuccessPostResponse;
import br.org.institutobushido.services.admin.AdminServices;
import jakarta.validation.Valid;

@RestController(value = "admin")
@RequestMapping("api/V1/admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminControllers {
    @Autowired
    private AdminServices adminServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("signup")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<SuccessPostResponse> signup(@Valid @RequestBody SignUpDTORequest signUpDTORequest) {
        SignUpDTOResponse admin = this.adminServices.signup(signUpDTORequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location)
                .body(new SuccessPostResponse(admin.email(), "Admin criado com sucesso", Admin.class.getSimpleName()));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTOResponse> login(@RequestBody @Valid LoginDTORequest loginDTORequest) {
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginDTORequest.email(),
                loginDTORequest.senha());
        Authentication auth = this.authenticationManager.authenticate(login);
        LoginDTOResponse token = this.adminServices.login((Admin) auth.getPrincipal());
        return ResponseEntity.ok().body(token);
    }
}
