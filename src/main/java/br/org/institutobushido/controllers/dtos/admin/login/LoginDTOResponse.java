package br.org.institutobushido.controllers.dtos.admin.login;

import br.org.institutobushido.models.admin.turmas.TurmaResponsavel;

import java.util.List;

public record LoginDTOResponse(String token, String role, List<TurmaResponsavel> turmas) {
}
