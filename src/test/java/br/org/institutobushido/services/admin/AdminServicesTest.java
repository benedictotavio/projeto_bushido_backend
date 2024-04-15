package br.org.institutobushido.services.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;
import br.org.institutobushido.controllers.dtos.admin.login.LoginDTOResponse;
import br.org.institutobushido.controllers.dtos.admin.signup.SignUpDTORequest;
import br.org.institutobushido.enums.admin.UserRole;
import br.org.institutobushido.models.admin.Admin;
import br.org.institutobushido.repositories.AdminRepositorio;

@SpringBootTest
@TestPropertySource(properties = { "jwt.secret=testSecretKey" })
public class AdminServicesTest {
    private SignUpDTORequest signUpDTORequest;

    @Mock
    private AdminRepositorio adminRepositorio;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private AdminServices adminServices;
    private Admin admin;

    public AdminServicesTest() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setUp() {
        adminServices = new AdminServices(adminRepositorio, mongoTemplate);

        admin = new Admin("admin", "admin@email.com", "admin", "admin", UserRole.ADMIN);

        signUpDTORequest = new SignUpDTORequest(
                "admin",
                "admin@email.com",
                "admin",
                "cargo",
                UserRole.ADMIN);
    }

    @Test
    public void deveCadastrarAdmin() {

        // Act
        adminServices.signup(signUpDTORequest);

        // Assert
        when(adminRepositorio.findByEmailAdmin(anyString())).thenReturn(Optional.of(admin));

        adminServices.signup(signUpDTORequest);

        assertNotNull(admin);
    }

    @Disabled("Teste pendente")
    @Test
    public void deveLogarAdmin() {
        // Act
        when(adminRepositorio.findByEmailAdmin(anyString())).thenReturn(Optional.of(admin));
        LoginDTOResponse response = adminServices.login(admin);

        // Assert
        assertNotNull(response.token());
        assertEquals(admin.getRole().getValue(), response.role());
    }
}
