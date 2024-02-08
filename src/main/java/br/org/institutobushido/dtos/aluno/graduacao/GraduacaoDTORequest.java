package br.org.institutobushido.dtos.aluno.graduacao;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record GraduacaoDTORequest(int kyu, int frequencia) {}
