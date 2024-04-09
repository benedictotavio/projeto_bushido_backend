package br.org.institutobushido.mappers.aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.institutobushido.controllers.dtos.aluno.graduacao.GraduacaoDTORequest;
import br.org.institutobushido.controllers.dtos.aluno.graduacao.GraduacaoDTOResponse;
import br.org.institutobushido.models.aluno.graduacao.Graduacao;

@SpringBootTest
class GraduacaoMapperTest {

    private Graduacao graduacao;

    private GraduacaoDTOResponse graduacaoDTOResponse;

    private GraduacaoDTORequest graduacaoDTORequest;

    @BeforeEach
    void setUp() {
        graduacao = new Graduacao(7);
        graduacaoDTORequest = new GraduacaoDTORequest(7);
        graduacaoDTOResponse = new GraduacaoDTOResponse(
                7,
                null,
                true,
                null,
                null,
                0,
                true,
                0,
                0
        );
    }

    @Test
    void deveRetornarGraduacaoDeGraduacaoDTORequest() {
        graduacao = GraduacaoMapper.mapToGraduacao(graduacaoDTORequest);
        assertEquals(graduacaoDTORequest.kyu(), graduacao.getKyu());
    }

    @Test
    void deveRetornarGraduacaoDTOResponseDeGraduacao() {
        graduacaoDTOResponse = GraduacaoMapper.mapToGraduacaoDTOResponse(graduacao);
        assertEquals(graduacao.getKyu(), graduacaoDTOResponse.kyu());
        assertEquals(graduacao.isAprovado(), graduacaoDTOResponse.aprovado());
        assertEquals(graduacao.getCargaHoraria(), graduacaoDTOResponse.cargaHoraria());
        assertEquals(graduacao.getDan(), graduacaoDTOResponse.dan());
        assertEquals(graduacao.getFrequencia(), graduacaoDTOResponse.frequencia());
        assertEquals(graduacao.getFaltas(), graduacaoDTOResponse.faltas());
        assertEquals(graduacao.isStatus(), graduacaoDTOResponse.status());
        assertEquals(graduacao.getFimGraduacao(), graduacaoDTOResponse.fimGraduacao());
        assertEquals(graduacao.getInicioGraduacao(), graduacaoDTOResponse.inicioGraduacao());
    }
}
