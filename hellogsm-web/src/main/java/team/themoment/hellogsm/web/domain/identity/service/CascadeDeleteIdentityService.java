package team.themoment.hellogsm.web.domain.identity.service;

/**
 * userid를 받아 Identity를 삭제하는 인터페이스입니다.
 */
public interface CascadeDeleteIdentityService {
    void execute(Long userId);
}
