package br.org.institutobushido.controllers.response.success;

import java.io.Serializable;
import java.util.List;

import br.org.institutobushido.models.admin.turmas.TurmaResponsavel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import lombok.Data;

@Getter
@Setter
public class SuccessLoginAuthenticated implements Serializable {
    private String token;
    private String role;
    private int status;
    private boolean success;
    private List<TurmaResponsavel> turmas;

    public SuccessLoginAuthenticated(String token, String role, List<TurmaResponsavel> turmas) {
        this.token = token;
        this.role = role;
        this.status = HttpStatus.OK.value();
        this.success = true;
        this.turmas = turmas;
    }
}
