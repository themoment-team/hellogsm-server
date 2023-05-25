package team.themoment.hellogsm.web.domain.user.service;

public interface ExistUserQuery {
    Boolean execute(String provider, String providerId);
}
