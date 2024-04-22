package br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.use_medicamento_continuo;

import br.org.institutobushido.controllers.dtos.aluno.historico_de_saude.informacoes_de_saude.uso_medicamento_continuo.UsoMedicamentoContinuoDTORequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UsoMedicamentoContinuoDTORequestTest {

    @Test
    public void usoMedicamentoContinuoDTORequestTest() {
        String tipo = "Uso Medicamento Continuo";

        UsoMedicamentoContinuoDTORequest usoMedicamentoContinuoDTORequest = new UsoMedicamentoContinuoDTORequest(tipo);

        assertEquals(tipo, usoMedicamentoContinuoDTORequest.tipo());
    }
}
