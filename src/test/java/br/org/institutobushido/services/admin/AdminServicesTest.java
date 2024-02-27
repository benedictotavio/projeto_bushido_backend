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

import br.org.institutobushido.dtos.admin.AdminDTORequest;
import br.org.institutobushido.dtos.admin.AdminDTOResponse;
import br.org.institutobushido.model.admin.Admin;
import br.org.institutobushido.repositories.AdminRepositorio;

@SpringBootTest
public class AdminServicesTest {
    private Admin admin;
    private AdminDTORequest adminDTORequest;
    private AdminDTOResponse adminDTOResponse;

    @BeforeEach
    void setUp() {
        admin = new Admin();

        adminDTORequest = AdminDTORequest.builder().withCargo("Chefe").withEmail("johndoe@example.com")
                .withNome("John Doe").build();

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
    void test_create_admin_with_valid_input() {
        // Arrange
        when(adminRepositorio.save(any(Admin.class))).thenReturn(admin);

        // Act
        AdminDTOResponse result = adminServices.signup(adminDTORequest);

        adminDTOResponse = AdminDTOResponse.builder().withEmail(admin.getEmail())
                .withNome(admin.getNome()).build();

        // Assert
        assertNotNull(result);
        assertEquals(adminDTOResponse.email(), result.email());
        assertEquals(adminDTOResponse.nome(), result.nome());
    }

    @Test
    void test_throw_exception_when_creating_admin_with_empty_name() {

        // Act
        adminDTORequest = AdminDTORequest.builder().withSenha("00000").withNome("test o2").withEmail(null).build();

        // Assert
        assertThrows(NullPointerException.class, () -> adminServices.signup(null));
    }
}