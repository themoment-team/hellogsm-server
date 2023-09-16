package team.themoment.hellogsm.web.domain.application.service;

/**
 * 현재 사용자의 원서를 최종제출하는 service interface 입니다.
 */
public interface FinalSubmissionService {
    void execute(Long userId);
}
