package br.org.institutobushido.controllers.dtos.turma.aluno;

import java.time.LocalDate;

import br.org.institutobushido.enums.aluno.Genero;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

public record AlunoDTORequest(
                @NotNull(message = "Nome do Aluno é obrigatório") String nome,
                @NotNull(message = "Data de nascimento do Aluno é obrigatório") @Past(message = "Data de nascimento deve ser no passado!") LocalDate dataNascimento,
                @NotNull(message = "Genero do Aluno é obrigatório") Genero genero,
                @NotNull(message = "Rg do Aluno é obrigatório!") @Pattern(regexp = "^\\d{9}$", message = "Formato de rg inválido!") String rg) {
}
