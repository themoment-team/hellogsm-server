package team.themoment.hellogsm.web.domain.user.service;

/**
 * user의 유뮤를 판별하는 interface입니다.
 */
public interface ExistUserQuery {
    Boolean execute(String provider, String providerId);
}
