package team.themoment.hellogsm.web.domain.identity.service;

import team.themoment.hellogsm.web.domain.identity.dto.request.IdentityReqDto;
import team.themoment.hellogsm.web.domain.identity.dto.response.CreateIdentityResDto;

/**
 * Identity를 생성하는 인터페이스입니다.
 */
public interface CreateIdentityService {
    /**
     * identity를 생성합니다.
     *
     * @param reqDto 생성할 Identity 정보가 담긴 DTO
     * @param userId 생성할 Identity에 대한 User의 userId
     * @return 생성된 Identity 정보가 담긴 DTO
     */
    CreateIdentityResDto execute(IdentityReqDto reqDto, Long userId);
}
