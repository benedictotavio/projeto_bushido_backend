package br.org.institutobushido.models.admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.org.institutobushido.enums.admin.UserRole;
import br.org.institutobushido.models.admin.turmas.TurmaResponsavel;
import br.org.institutobushido.utils.resources.exceptions.AlreadyRegisteredException;
import br.org.institutobushido.utils.resources.exceptions.EntityNotFoundException;
import lombok.Data;

@Data
@Document(collection = "admin")
public class Admin implements UserDetails {
    private String nome;

    @Indexed(unique = true, background = true)
    private String email;

    private String senha;
    private String cargo;
    private UserRole role;
    private List<TurmaResponsavel> turmas;

    public Admin(String nome, String email, String senha, String cargo, UserRole role) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cargo = cargo;
        this.role = role;
        this.turmas = new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        }

        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public TurmaResponsavel adicionarTurma(TurmaResponsavel novaTurma) {
        for (TurmaResponsavel turma : this.turmas) {
            if (turma.getNome().equalsIgnoreCase(novaTurma.getNome())) {
                throw new AlreadyRegisteredException("Turma ja existe");
            }
        }
        this.turmas.add(novaTurma);
        return novaTurma;
    }

    public String removerTurma(String nomeTurma) {
        for (TurmaResponsavel turmaResponsavel : this.getTurmas()) {
            if (turmaResponsavel.getNome().equalsIgnoreCase(nomeTurma)) {
                this.turmas.remove(turmaResponsavel);
                return turmaResponsavel.getNome();
            }
        }
        throw new EntityNotFoundException(this.getCargo() + " nao possui turma com o nome " + nomeTurma);
    }
}
