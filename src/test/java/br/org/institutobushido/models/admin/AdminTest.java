package br.org.institutobushido.models.admin;

import br.org.institutobushido.models.admin.turmas.TurmaResponsavel;
import br.org.institutobushido.providers.utils.resources.exceptions.AlreadyRegisteredException;
import br.org.institutobushido.providers.utils.resources.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import br.org.institutobushido.providers.enums.admin.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminTest {
    private Admin admin;
    private TurmaResponsavel turmaResponsavel;
    private String email;
    private String nome;
    private String senha;
    private String cargo;

    @BeforeEach
    void setUp() {

        email = "admin@email.com";
        nome = "admin";
        senha = "admin";
        cargo = "admin";

        admin = new Admin(nome, email, senha, cargo, UserRole.ADMIN);

        turmaResponsavel = new TurmaResponsavel("Tutor", "Turma A");
    }

    @Test
    void deveInstanciarClasseAdmin() {
        assertEquals(nome, admin.getNome());
        assertEquals(email, admin.getEmail());
        assertEquals(senha, admin.getSenha());
        assertEquals(senha, admin.getPassword());
        assertEquals(email, admin.getUsername());
        assertEquals(cargo, admin.getCargo());
        assertEquals(UserRole.ADMIN, admin.getRole());
        assertTrue(admin.isAccountNonExpired());
        assertTrue(admin.isAccountNonLocked());
        assertTrue(admin.isCredentialsNonExpired());
        assertTrue(admin.isEnabled());
        assertTrue(admin.getTurmas().isEmpty());
    }

    @Test
    void deveAdicionarTurmaResponsavel() {
        TurmaResponsavel result = admin.adicionarTurma(turmaResponsavel);

        assertEquals(turmaResponsavel, result);
        assertTrue(admin.getTurmas().contains(turmaResponsavel));
    }

    @Test
    void deveRemoverTurmaResponsavel() {
        admin.adicionarTurma(turmaResponsavel);
        String result = admin.removerTurma(turmaResponsavel.getNome());

        assertEquals(turmaResponsavel.getNome(), result);
        assertFalse(admin.getTurmas().contains(turmaResponsavel));
    }

    @Test
    void deveRetornarAutorizacao() {
        Collection<? extends GrantedAuthority> authorities = admin.getAuthorities();
        List<GrantedAuthority> expectedAuthorities = List.of(
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_USER")
        );
        assertEquals(expectedAuthorities, authorities);
    }

    @Test
    void deveRetornarEmail() {
        admin.setRole(UserRole.TUTOR);
        Collection<? extends GrantedAuthority> authorities = admin.getAuthorities();
        List<GrantedAuthority> expectedAuthorities = List.of(
                new SimpleGrantedAuthority("ROLE_USER")
        );
        assertEquals(expectedAuthorities, authorities);
    }

    @Test
    void deveAdicionarTurma() {
        admin.adicionarTurma(turmaResponsavel);
        assertEquals(1, admin.getTurmas().size());
    }

    @Test
    void deveRemoverTurma() {
        admin.adicionarTurma(turmaResponsavel);
        admin.removerTurma(turmaResponsavel.getNome());
        assertEquals(0, admin.getTurmas().size());
    }

    @Test
    void deveLancarExcecaoQuandoTurmaNaoExiste() {
        assertThrows(EntityNotFoundException.class, () -> admin.removerTurma(turmaResponsavel.getNome()));
    }

    @Test
    void deveLancarExcecaoQuandoTurmaJaExiste() {
        admin.adicionarTurma(turmaResponsavel);
        assertThrows(AlreadyRegisteredException.class, () -> admin.adicionarTurma(turmaResponsavel));
    }
}
