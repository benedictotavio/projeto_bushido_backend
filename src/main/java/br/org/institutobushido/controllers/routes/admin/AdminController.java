package br.org.institutobushido.controllers.routes.admin;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import br.org.institutobushido.controllers.response.error.StandardError;
import br.org.institutobushido.controllers.response.success.SuccessPostResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.ErrorResponse;
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
import br.org.institutobushido.services.admin.AdminServicesInterface;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController(value = "admin")
@RequestMapping("api/V1/admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {

        private AdminServicesInterface adminServices;
        private AuthenticationManager authenticationManager;

        private static final String URI_ADMIN = "/api/V1/admin";

        public AdminController(AdminServicesInterface adminServices, AuthenticationManager authenticationManager) {
                this.adminServices = adminServices;
                this.authenticationManager = authenticationManager;
        }

        @Operation(summary = "Criar novo Admin | Tutor",
                responses = {
                        @ApiResponse(description = "Admin criado com sucesso", responseCode = "201",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = SuccessPostResponse.class))),
                        @ApiResponse(description = "Erro ao criar admin", responseCode = "400",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = StandardError.class)))
                })
        @PostMapping("signup")
        public ResponseEntity<SuccessPostResponse> signup(@Valid @RequestBody SignUpDTORequest signUpDTORequest)
                        throws URISyntaxException {
                this.adminServices.signup(signUpDTORequest);
                return ResponseEntity.created(
                                new URI(URI_ADMIN))
                                .body(new SuccessPostResponse(signUpDTORequest.email(),
                                                "Admin criado com sucesso.",
                                                Admin.class.getSimpleName()));
        }

        @Operation(summary = "Login do Admin | Tutor",
                responses = {
                        @ApiResponse(description = "Login realizado com sucesso", responseCode = "200",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = SuccessLoginAuthenticated.class))),
                        @ApiResponse(description = "Erro ao realizar login", responseCode = "401",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = StandardError.class)))
                })
        @PostMapping("login")
        public ResponseEntity<SuccessLoginAuthenticated> login(@Valid @RequestBody LoginDTORequest loginDTORequest) {
                UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(
                                loginDTORequest.email(),
                                loginDTORequest.senha());
                Authentication auth = this.authenticationManager.authenticate(login);
                LoginDTOResponse admin = this.adminServices.login((Admin) auth.getPrincipal());
                return ResponseEntity.ok().body(
                                new SuccessLoginAuthenticated(admin.token(), admin.role(), admin.turmas()));
        }

        @Operation(summary = "Buscar admins por nome",
                responses = {
                        @ApiResponse(description = "Lista de admins retornada com sucesso", responseCode = "200",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = AdminDTOResponse.class))),
                        @ApiResponse(description = "Erro ao buscar admins", responseCode = "400",
                                content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = StandardError.class)))
                })
        @GetMapping("buscar")
        public List<AdminDTOResponse> buscarAdmins(@RequestParam(name = "nome") String nome) {
                return this.adminServices.buscarAdminPorNome(nome);
        }
}
