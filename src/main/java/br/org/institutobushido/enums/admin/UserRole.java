package br.org.institutobushido.enums.admin;

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
