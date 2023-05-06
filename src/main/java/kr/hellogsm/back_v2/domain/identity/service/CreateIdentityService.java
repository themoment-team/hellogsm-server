package kr.hellogsm.back_v2.domain.identity.service;

import kr.hellogsm.back_v2.domain.identity.dto.request.CreateIdentityReqDto;
import kr.hellogsm.back_v2.domain.identity.dto.domain.IdentityDto;

/**
 * identityReqDto와 userId를 받아 Identity를 생성하는 인터페이스입니다.
 *
 *  * @author 양시준
 *  * @since 1.0.0
 */
public interface CreateIdentityService {
    IdentityDto execute(CreateIdentityReqDto identityReqDto, Long userId);
}
