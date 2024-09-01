package br.org.institutobushido.controllers.response.success;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class SuccessPostResponse implements Serializable {
    private String id;
    private int status;
    private String message;
    private String entity;

    public SuccessPostResponse(String id, String message) {
        this.id = id;
        this.status = HttpStatus.OK.value();
        this.message = message;
    }

    public SuccessPostResponse(String id, String message, int status) {
        this.id = id;
        this.status = status;
        this.message = message;
    }

    public SuccessPostResponse(String id, String message, String entity) {
        this.id = id;
        this.entity = entity;
        this.status = HttpStatus.OK.value();
        this.message = message;
    }

    public SuccessPostResponse(String id, String message,int status, String entity) {
        this.id = id;
        this.entity = entity;
        this.status = status;
        this.message = message;
    }
}
