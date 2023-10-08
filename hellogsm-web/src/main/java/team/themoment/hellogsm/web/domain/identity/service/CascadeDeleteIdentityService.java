package team.themoment.hellogsm.web.domain.identity.service;

/**
 * Identity를 삭제하는 인터페이스입니다.
 */
public interface CascadeDeleteIdentityService {
    /**
     * identity를 삭제합니다.
     *
     * @param userId 삭제할 identity에 대한 user의 userId
     */
    void execute(Long userId);
}
