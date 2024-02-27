package br.org.institutobushido.model.admin;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "admin")
public class Admin {
    private String nome;
    
    @Indexed(unique = true, background = true)
    private String email;

    private String senha;
    private String cargo;
}
