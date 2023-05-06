package kr.hellogsm.back_v2.domain.identity.service;

import kr.hellogsm.back_v2.domain.identity.dto.domain.IdentityDto;

/**
 * 주어진 userId에 해당하는 Identity를 조회하는 인터페이스입니다.
 *
 *  * @author 양시준
 *  * @since 1.0.0
 */
public interface IdentityQuery {
    IdentityDto execute(Long userId);
}
