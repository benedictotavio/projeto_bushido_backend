package br.org.institutobushido.services.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import br.org.institutobushido.dtos.admin.signup.SignUpDTORequest;
import br.org.institutobushido.dtos.admin.signup.SignUpDTOResponse;
import br.org.institutobushido.enums.admin.UserRole;
import br.org.institutobushido.model.admin.Admin;
import br.org.institutobushido.repositories.AdminRepositorio;

@SpringBootTest
class AdminServicesTest {
    private Admin admin;
    private SignUpDTORequest adminDTORequest;
    private SignUpDTOResponse adminDTOResponse;

    @BeforeEach
    void setUp() {
        admin = new Admin();

        adminDTORequest = SignUpDTORequest.builder().withSenha("123").withCargo("Chefe")
                .withEmail("johndoe@example.com")
                .withNome("John Doe").withRole(UserRole.ADMIN).build();

        admin.setEmail(adminDTORequest.email());
        admin.setCargo(adminDTORequest.cargo());
        admin.setNome(adminDTORequest.nome());

        reset(adminRepositorio, mongoTemplate);
    }

    @Mock
    private AdminRepositorio adminRepositorio;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private AdminServices adminServices;

    @Test
    void criarAdminComDadosValidos() {
        // Arrange
        when(adminRepositorio.save(any(Admin.class))).thenReturn(admin);

        // Act
        SignUpDTOResponse result = adminServices.signup(adminDTORequest);

        adminDTOResponse = SignUpDTOResponse.builder().withEmail(admin.getEmail())
                .withNome(admin.getNome()).build();

        // Assert
        assertNotNull(result);
        assertEquals(adminDTOResponse.email(), result.email());
        assertEquals(adminDTOResponse.nome(), result.nome());
    }

    @Test
    void deveLancarUmaExcecaoQuandoEmailForNulo() {

        // Act
        adminDTORequest = SignUpDTORequest.builder().withSenha("00000").withNome("test o2").withEmail(null).build();

        // Assert
        assertThrows(NullPointerException.class, () -> adminServices.signup(null));
    }
}