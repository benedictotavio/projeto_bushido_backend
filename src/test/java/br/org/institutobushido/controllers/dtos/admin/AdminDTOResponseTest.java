package br.org.institutobushido.controllers.dtos.admin;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AdminDTOResponseTest {
    @Test
    public void test_create_instance_with_valid_parameters() {
        // Arrange
        String nome = "John";
        String email = "john@example.com";
        String role = "admin";
        String cargo = "manager";

        // Act
        AdminDTOResponse adminDTO = new AdminDTOResponse(nome, email, role, cargo);

        // Assert
        assertNotNull(adminDTO);
        assertEquals(nome, adminDTO.nome());
        assertEquals(email, adminDTO.email());
        assertEquals(role, adminDTO.role());
        assertEquals(cargo, adminDTO.cargo());
    }
}
