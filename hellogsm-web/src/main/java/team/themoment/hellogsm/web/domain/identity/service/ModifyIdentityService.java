package team.themoment.hellogsm.web.domain.identity.service;

import team.themoment.hellogsm.web.domain.identity.dto.domain.IdentityDto;
import team.themoment.hellogsm.web.domain.identity.dto.request.IdentityReqDto;

/**
 * identityReqDto와 userId를 받아 Identity를 생성하는 인터페이스입니다.
 *
 *  * @author 양시준
 *  * @since 1.0.0
 */
public interface ModifyIdentityService {
    IdentityDto execute(IdentityReqDto reqDto, Long userId);
}
