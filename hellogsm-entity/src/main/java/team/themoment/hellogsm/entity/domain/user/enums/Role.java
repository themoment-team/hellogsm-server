package team.themoment.hellogsm.entity.domain.user.enums;

public enum Role {

    ROLE_UNAUTHENTICATED("UNAUTHENTICATED"),
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
