package br.org.institutobushido.controllers.routes.admin;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import br.org.institutobushido.controllers.dtos.admin.AdminDTOResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.org.institutobushido.controllers.dtos.admin.login.LoginDTORequest;
import br.org.institutobushido.controllers.dtos.admin.login.LoginDTOResponse;
import br.org.institutobushido.controllers.dtos.admin.signup.SignUpDTORequest;
import br.org.institutobushido.controllers.response.success.SuccessLoginAuthenticated;
import br.org.institutobushido.controllers.response.success.SuccessPostResponse;
import br.org.institutobushido.enums.admin.UserRole;
import br.org.institutobushido.models.admin.Admin;
import br.org.institutobushido.services.admin.AdminServicesInterface;
import java.net.URISyntaxException;
import java.util.List;

@ExtendWith(SpringExtension.class)
class AdminControllerTest {

    private SignUpDTORequest signUpDTORequest;
    private LoginDTOResponse loginDTOResponse;
    private LoginDTORequest loginDTORequest;
    private AdminDTOResponse adminDTOResponse;

    @InjectMocks
    private AdminController adminController;

    @Mock
    private AdminServicesInterface adminServices;

    @Mock
    private AuthenticationManager authenticationManager;

    private Admin admin;

    @BeforeEach
    void setUp() {
        signUpDTORequest = new SignUpDTORequest("NOME", "email@email.com.br", "senha", "CARGO", UserRole.ADMIN);
        admin = new Admin("NOME", "email@email.com.br", "senha", "CARGO", UserRole.ADMIN);
        loginDTOResponse = new LoginDTOResponse("token", UserRole.ADMIN.getValue(), admin.getTurmas());
        loginDTORequest = new LoginDTORequest("email@email.com.br", "senha");
        adminDTOResponse = new AdminDTOResponse("NOME", "email@email.com.br", "CARGO", UserRole.ADMIN.getValue());

        openMocks(this);
    }

    @Test
    void deveCriarUmNovoAdmin() throws URISyntaxException {
        // Act
        SuccessPostResponse response = adminController.signup(signUpDTORequest).getBody();

        // Assert
        assert response != null;
        assertEquals("Admin criado com sucesso.", response.getMessage());
        assertEquals(Admin.class.getSimpleName(), response.getEntity());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void deveRetornarTokenDeLogin_200() {
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(adminServices.login(any())).thenReturn(loginDTOResponse);
        ResponseEntity<SuccessLoginAuthenticated> response = adminController.login(loginDTORequest);

        assert response != null;
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(loginDTOResponse.token(), response.getBody().getToken());
        assertEquals(loginDTOResponse.role(), response.getBody().getRole());
    }

    @Test
    void deveLancarExcecao_401() {
        when(authenticationManager.authenticate(any())).thenThrow(
                new AuthenticationException("Invalid credentials") {
                    @Override
                    public String getMessage() {
                        return "Invalid credentials";
                    }
                });
        assertThrows(AuthenticationException.class, () -> adminController.login(loginDTORequest));
    }

    @Test
    void deveRetornarTodosOsAdmins() {
        // Arrange
        String validName = "John Doe";

        // Act
        when(adminServices.buscarAdminPorNome(validName)).thenReturn(List.of(adminDTOResponse));
        List<AdminDTOResponse> result = new AdminController(adminServices, authenticationManager).buscarAdmins(validName);

        // Assert
        assertNotNull(result);
        assertEquals(AdminDTOResponse.class, result.get(0).getClass());
    }
}