package br.org.institutobushido.dtos.aluno;

import com.mongodb.lang.NonNull;

import br.org.institutobushido.enums.FiliacaoResposavel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record ResponsavelDTORequest(
    @NotEmpty(message = "Nome é obrigatório!")
    String nome,

    @NonNull() @Pattern(regexp = "[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}",
            message = "Formato de cpf inválido!")
    String cpf,

    @NonNull() @Pattern(regexp = "^\\(?[1-9]{2}\\)? ?(?:[0-9]{4,5})\\-?[0-9]{4}",
            message = "Formato de telefone inválido!")
    String telefone,

    @NonNull() @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}",
            message = "Formato de email inválido!")
    String email,

    @NonNull
    FiliacaoResposavel filiacao
){}
