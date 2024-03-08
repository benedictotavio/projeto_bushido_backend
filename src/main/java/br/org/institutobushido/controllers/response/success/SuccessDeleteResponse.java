package br.org.institutobushido.controllers.response.success;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class SuccessDeleteResponse implements Serializable {
    private String id;
    private int status;
    private boolean success;
    private String message;
    private String entity;

    public SuccessDeleteResponse(String id, String message) {
        this.id = id;
        this.message = message;
        this.success = true;
        this.status = HttpStatus.OK.value();
    }

    public SuccessDeleteResponse(String id, String message, String entity) {
        this.id = id;
        this.entity = entity;
        this.message = message;
        this.status = HttpStatus.OK.value();
        this.success = true;
    }
}
