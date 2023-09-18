package team.themoment.hellogsm.web.domain.identity.service;

import team.themoment.hellogsm.web.domain.identity.dto.request.GenerateCodeReqDto;

/**
 * code를 생성하는 인터페이스입니다.
 */
public interface GenerateCodeService {
    /**
     * code를 생성합니다.
     *
     * @param userId 코드를 생성하기 위한 user의 userId
     * @param reqDto 코드를 생성하기 위한 정보가 담긴 DTO
     * @return 생성된 code가 반환됩니다.
     */
    String execute(Long userId, GenerateCodeReqDto reqDto);
}
