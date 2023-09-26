package team.themoment.hellogsm.web.domain.identity.service;

import team.themoment.hellogsm.web.domain.identity.dto.domain.IdentityDto;

/**
 * identity를 조회하는 인터페이스입니다.
 */
public interface IdentityQuery {
    /**
     * identity를 조회합니다.
     *
     * @param userId 조회할 identity의 userId
     * @return 조회한 identity의 정보가 담긴 DTO
     */
    IdentityDto execute(Long userId);
}
