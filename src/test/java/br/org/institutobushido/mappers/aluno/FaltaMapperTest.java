package br.org.institutobushido.mappers.aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.institutobushido.controllers.dtos.aluno.graduacao.faltas.FaltaDTOResponse;
import br.org.institutobushido.models.aluno.graduacao.falta.Falta;

@SpringBootTest
class FaltaMapperTest {
    private Falta falta;
    private FaltaDTOResponse faltaDTO;

    @BeforeEach
    void setUp() {
        falta = new Falta(
                "motivo",
                "observacao",
                new Date(new Date().getTime() - 2000 * 60 * 60 * 24 * 4));
        faltaDTO = new FaltaDTOResponse(
                falta.getData(),
                falta.getMotivo(),
                falta.getObservacao()
        );
    }

    @Test
    void deveRetornarFaltaDeFaltaDTOResponse() {
        falta = FaltaMapper.mapToFalta(faltaDTO);
        assertEquals(faltaDTO.data(), falta.getData());
        assertEquals(faltaDTO.motivo(), falta.getMotivo());
        assertEquals(faltaDTO.observacao(), falta.getObservacao());
    }

    @Test
    void deveRetornarFaltaDTOResponseDeFalta() {
        faltaDTO = FaltaMapper.mapToFaltaDTOResponse(falta);
        assertEquals(falta.getData(), faltaDTO.data());
        assertEquals(falta.getMotivo(), faltaDTO.motivo());
        assertEquals(falta.getObservacao(), faltaDTO.observacao());
    }

}
