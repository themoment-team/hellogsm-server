package team.themoment.hellogsm.web.domain.identity.service;

import team.themoment.hellogsm.web.domain.identity.dto.request.IdentityReqDto;
import team.themoment.hellogsm.web.domain.identity.dto.response.CreateIdentityResDto;

/**
 * identityReqDto와 userId를 받아 Identity를 생성하는 인터페이스입니다.
 */
public interface CreateIdentityService {
    CreateIdentityResDto execute(IdentityReqDto reqDto, Long userId);
}
