package br.org.institutobushido.providers.enums.aluno;

public enum CorDePele {

    BRANCO("Branco"),
    PRETO("Preto"),
    AMARELO("Amarelo"),
    INDIGENA("Indígena"),
    PARDO("Pardo");

    private final String cor;

    CorDePele(String cor) {
        this.cor = cor;
    }

    public String getCor() {
        return cor;
    }
}
