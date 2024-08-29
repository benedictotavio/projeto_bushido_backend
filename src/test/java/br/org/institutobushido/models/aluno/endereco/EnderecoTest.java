package br.org.institutobushido.models.aluno.endereco;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EnderecoTest {
    private Endereco endereco;

    @BeforeEach
    void setUp() {
        endereco = new Endereco();
    }

    @Test
    void deveInstanciarClasseEndereco() {
        endereco.setCep("212311111");
        endereco.setCidade("Cidade");
        endereco.setEstado("Estado");
        endereco.setLogradouro("Logradouro");
        endereco.setNumero("123");
        assertEquals("212311111", endereco.getCep());
        assertEquals("Cidade", endereco.getCidade());
        assertEquals("Estado", endereco.getEstado());
        assertEquals("Logradouro", endereco.getLogradouro());
        assertEquals("123", endereco.getNumero());
    }

    @Test
    void naoDeveAlterarValoresVazios() {
        endereco = new Endereco("Cidade", "Estado", "4546554448", "1", "Rua Logradouro, 12");
        endereco.setLogradouro("");
        endereco.setCidade("");
        endereco.setCep("");
        endereco.setNumero("");
        endereco.setEstado("");
        assertEquals("Cidade", endereco.getCidade());
        assertEquals("Estado", endereco.getEstado());
        assertEquals("4546554448", endereco.getCep());
        assertEquals("1", endereco.getNumero());
        assertEquals("Rua Logradouro, 12", endereco.getLogradouro());

    }
}
