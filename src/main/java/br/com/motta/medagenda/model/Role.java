package br.com.motta.medagenda.model;

public enum Role {
    PACIENTE("paciente"),
    MEDICO("medico"),
    ADMIN("admin"),
    ;

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
