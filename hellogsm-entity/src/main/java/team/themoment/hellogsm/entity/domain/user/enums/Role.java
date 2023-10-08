package team.themoment.hellogsm.entity.domain.user.enums;

public enum Role {

    ROLE_UNAUTHENTICATED("UNAUTHENTICATED"),
    ROLE_USER("USER"),
    ROLE_TESTER("TESTER"), // prod 환경에서 기간 상관없이 테스트 가능한 USER
    ROLE_ADMIN("ADMIN");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
