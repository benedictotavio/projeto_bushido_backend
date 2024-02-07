package br.org.institutobushido.enums;

public enum FatorRH {
    POSITIVO("+"),
    NEGATIVO("-");

    private final String simbolo;

    FatorRH(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getSimbolo() {
        return simbolo;
    }
}
