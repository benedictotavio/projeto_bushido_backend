package br.org.institutobushido.enums;

public enum InformacaoSaude {
    ALERGIAS("alergias"),
    CIRURGIA("cirurgia"),
    DOENCA_CRONICA("doencaCronica"),
    USO_MEDICAMENTO_CONTINUO("usoMedicamentoContinuo");

    private final String infoSaude;

    InformacaoSaude(String infoSaude) {
        this.infoSaude = infoSaude;
    }

    public String getInformacaoSaude() {
        return infoSaude;
    }

}
