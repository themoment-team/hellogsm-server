package team.themoment.hellogsm.web.domain.application.service;

/**
 * 현재 사용자의 원서를 삭제하는 service interface 입니다.
 */
public interface DeleteApplicationService {
    void execute(Long userId);
}
