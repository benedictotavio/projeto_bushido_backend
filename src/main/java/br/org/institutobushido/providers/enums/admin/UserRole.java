package br.org.institutobushido.providers.enums.admin;

public enum UserRole {
    ADMIN("admin"),
    TUTOR("tutor"),;

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getValue() {
        return role;
    }
}
