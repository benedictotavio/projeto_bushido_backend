package br.org.institutobushido.controllers.dtos.aluno.graduacao;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record GraduacaoDTORequest(int kyu, int frequencia) {}
