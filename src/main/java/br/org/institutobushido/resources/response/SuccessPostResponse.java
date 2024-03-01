package br.org.institutobushido.resources.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessPostResponse  implements Serializable{
    private String id;
    private int status;
    private String message;
    private String entity;

    public SuccessPostResponse(String id, int status, String message) {
        this.id = id;
        this.status = status;
        this.message = message;
    }

}
