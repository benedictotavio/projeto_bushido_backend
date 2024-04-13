package br.org.institutobushido.controllers.response.success;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class SuccessLoginAuthenticated implements Serializable {
    private String token;
    private String role;
    private int status;
    private boolean success;

    public SuccessLoginAuthenticated(String token, String role) {
        this.token = token;
        this.role = role;
        this.status = HttpStatus.OK.value();
        this.success = true;
    }
}
