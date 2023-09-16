package team.themoment.hellogsm.web.domain.application.service;

import team.themoment.hellogsm.entity.domain.identity.entity.Identity;

/**
 * Identity 수정 시 원서를 수정하는 service interface 입니다.
 */
public interface ModifyApplicationForConsistency {
    void execute(Identity identity);
}
