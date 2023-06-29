package team.themoment.hellogsm.web.domain.identity.service;

import team.themoment.hellogsm.web.domain.identity.dto.request.GenerateCodeReqDto;

public interface GenerateCodeService {
    String execute(Long userId, GenerateCodeReqDto reqDto);
}
