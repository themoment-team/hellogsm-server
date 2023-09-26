package team.themoment.hellogsm.web.domain.identity.service;

import team.themoment.hellogsm.web.domain.identity.dto.domain.IdentityDto;
import team.themoment.hellogsm.web.domain.identity.dto.request.IdentityReqDto;

/**
 * identity를 수정하는 인터페이스입니다.
 */
public interface ModifyIdentityService {
    /**
     * identity를 수정합니다.
     *
     * @param reqDto 수정할 identity 정보가 담긴 DTO
     * @param userId 수정할 identity에 대한 user의 userId
     * @return 수정한 identity 정보가 담긴 DTO
     */
    IdentityDto execute(IdentityReqDto reqDto, Long userId);
}
