package br.org.institutobushido.mappers.admin;

import br.org.institutobushido.controllers.dtos.admin.AdminDTOResponse;
import br.org.institutobushido.enums.admin.UserRole;
import br.org.institutobushido.models.admin.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AdminMapperTest {
    private Admin admin;

    @BeforeEach
    public void setUp() {
        admin = new Admin("admin", "admin@email.com", "admin", "admin", UserRole.ADMIN);
    }

    @Test
    void test_same_values_as_input_admin_object() {
        // Act
        AdminDTOResponse response = AdminMapper.mapToAdminDTOResponse(admin);

        // Assert
        assertEquals(admin.getNome(), response.nome());
        assertEquals(admin.getEmail(), response.email());
        assertEquals(admin.getCargo(), response.cargo());
        assertEquals(admin.getRole().toString(), response.role());
    }
}
