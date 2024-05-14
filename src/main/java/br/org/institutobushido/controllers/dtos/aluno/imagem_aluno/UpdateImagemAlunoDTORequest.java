package br.org.institutobushido.controllers.dtos.aluno.imagem_aluno;

import org.springframework.web.multipart.MultipartFile;

public record UpdateImagemAlunoDTORequest(
        MultipartFile imagemMultipart
) {
}
