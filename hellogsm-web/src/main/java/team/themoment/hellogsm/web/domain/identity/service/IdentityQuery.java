package team.themoment.hellogsm.web.domain.identity.service;

import team.themoment.hellogsm.web.domain.identity.dto.domain.IdentityDto;

/**
 * 주어진 userId에 해당하는 Identity를 조회하는 인터페이스입니다.
 *
 *  * @author 양시준
 *  * @since 1.0.0
 */
public interface IdentityQuery {
    IdentityDto execute(Long userId);
}
